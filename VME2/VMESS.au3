#Region ;**** Directives created by AutoIt3Wrapper_GUI ****
#AutoIt3Wrapper_Outfile=vmess.Exe
#EndRegion ;**** Directives created by AutoIt3Wrapper_GUI ****
#include "_RunWaitGet.au3"
#include "VME2.au3"
;	VMESS:   VME SIMULATED SIMULATION

;Esto hay que eliminarlo
;$cmdLine = __getArray()
;__add($cmdLine, "sim")
;__add($cmdLine, "C:\Users\Margaret\AppData\Roaming\HardScratch\test")
;__add($cmdLine, "miden:'1'|cosa:'0'")

$path = __getArgument(2)
$pathSim = $path & "\sim"
switch(__getArgument(1))
	case "check"
		compile($path&"\circuit.b0ve",$path&"\code.vhdl")

		$log = ghdl("-a",$path&"\code.vhdl")
		_detectError($log)
		__Error(0,0)
	case "init"
		If FileExists($pathSim) Then DirRemove($pathSim,1)
		DirCreate($pathSim)

		compile($path&"\circuit.b0ve",$pathSim&"\base.vhdl", true)
		$response = _modifyModels($pathSim, __getArgument(3), True)
		_detectRuntimeError($response)
		ConsoleWrite(_createOut($response)&@CRLF)
	case "sim"
		_loadIDLINES($pathSim&"\IDLINES.B0vE")
		$response = _modifyModels($pathSim, __getArgument(3), False)
		_detectRuntimeError($response)
		ConsoleWrite(_createOut($response)&@CRLF)
EndSwitch



Func _detectError($text)
	$logs = StringSplit($text,@CRLF)
	For $i = 1 To $logs[0]
		$log = StringTrimLeft($logs[$i],2)
		$parts = StringSplit($log,":")
		If $parts[0] < 4 Then ContinueLoop
		$log = ""
		For $j = 4 To $parts[0]
			$log &= $parts[$j]
		Next
		__checkLog($parts[2], $log)
	Next
EndFunc
Func __checkLog($line, $log)
	$code = 0
	ConsoleWrite($log&"  "&$line&"  ---> ")

	If StringInStr($log, "operator") <> 0 And StringInStr($log, "is overloaded") <> 0 Then
		;Error de operador mal usado
		$operator = __getInComas($log)
		Switch($operator)
			Case "+"
				$code = $ERROR_ILEGAL_USE_OF_OPERATOR_ADD
			Case "-"
				$code = $ERROR_ILEGAL_USE_OF_OPERATOR_SUB
			Case "*"
				$code = $ERROR_ILEGAL_USE_OF_OPERATOR_TIM
			Case "/"
				$code = $ERROR_ILEGAL_USE_OF_OPERATOR_TAK
			Case "&"
				$code = $ERROR_ILEGAL_USE_OF_OPERATOR_CONCATENATE
		EndSwitch
	ElseIf StringInStr($log, "can't match") <> 0 Then
		;Error de mala inicializacion
		$code = $ERROR_BAD_INICIALIZATION
	;TODO: AQUI HAY QUE AGREGAR UN PORRON DE ERRORES QUE SUELTA GHDL
	EndIf


	ConsoleWrite($ERROR[$code]&@CRLF)
	If $code <> 0 Then __Error($code, $IDLINES[$line])
EndFunc
Func _detectRuntimeError($text)
	If StringInStr($text, "simulation failed") = 0 Then Return
	$line = StringSplit($text,@CRLF)
	$line = $line[1]
	$log = StringTrimLeft($line, StringInStr($line,"error:")+6)
	$log = StringMid($log, 1, StringInStr($log, "at ")-2)
	$line = StringTrimLeft($line, StringInStr($line,":",2,-1))

	If StringInStr($log, "bound check failure") <> 0 Then __Error($ERROR_CANT_CHANGE_LENGTH_OF_BITARRAY, $IDLINES[$line])
EndFunc

Func __getInComas($text)
	$text = StringTrimLeft($text,StringInStr($text,'"'))
	$text = StringTrimRight($text, StringLen($text)-StringInStr($text,'"',0,-1)+1)
	Return StringReplace($text,'"',"")
EndFunc
Func _loadIDLINES($file)
	$IDLINES = StringSplit(StringReplace(FileRead($file),@CRLF,""),"|")
EndFunc

Func _modifyModels($path, $text, $firstTime); Init True: No se conocen las señales, hay que leerlas del compilador $VARIABLES
	$file = $path&"\sim.vhdl"
	$fileTest = $path&"\simTest.vhdl"
	$model = $path&"\base.vhdl"
	$modelTest = $path&"\baseTest.vhdl"
	If FileExists($file) Then FileDelete($file)
	If FileExists($fileTest) Then FileDelete($fileTest)

	$inits = __getArray()
	If $firstTime Then
		For $i = 1 To $VARIABLES[0]
			$var = $VARIABLES[$i]
			If $var[3] = 3 And $var[6] <> "-1" Then
				$init = __getArray()
				__add($init,$var[2])
				__add($init,_decodeConstructor($var[6], -1))
				__add($inits,$init)
			EndIf
		Next
	Else
		$parts = StringSplit($text,"|")
		For $i = 1 To $parts[0]
			$subParts = StringSplit($parts[$i],":")
			$init = __getArray()
			__add($init,$subParts[1])
			__add($init,$subParts[2])
			__add($inits,$init)
		Next
	EndIf


	$Text_file = FileRead($model)
	$Text_fileTest = FileRead($modelTest)
	For $i = 1 To $inits[0]
		$init = $inits[$i]
		$Text_file = StringReplace($Text_file, "<"&$init[1]&">", ":= "&$init[2])
		$Text_fileTest = StringReplace($Text_fileTest, "<"&$init[1]&">", ":="&$init[2])
	Next
	$Text_file = _deleteMarks($Text_file)
	$Text_fileTest = _deleteMarks($Text_fileTest)
	FileWrite($file,$Text_file)
	FileWrite($fileTest,$Text_fileTest)

	ghdl("--clean", "")
	ghdl("-a", $file)
	ghdl("-a", $fileTest)
	Return ghdl("-r","HardScratchSimulation_Entity", True, True)
EndFunc
Func _deleteMarks($text)
	$first = False
	$distance = 0

	$i = 1
	While $i < StringLen($text)
		$c = StringMid($text,$i,1)
		If $first Then
			If $c = ">" Then
				$text = StringMid($text, 1, $i-$distance-2)&StringMid($text, $i+1, -1)
				$i -= $distance+1
				$first = False
			Else
				If $distance >= 8 Then
					$first = False
				Else
					$distance += 1
				EndIf
				$i += 1
			EndIf
		Else
			If $c = "<" Then
				$first = True
				$distance = 0
			EndIf
			$i += 1
		EndIf
	WEnd

	Return $text
EndFunc
Func _createOut($text)
	$lines = StringSplit($text,@CRLF)
	$outputs = ""
	$signals = ""
	;MsgBox(0,"SALIDA GHDL",$text)

	For $i = 1 To $lines[0]
		$line = $lines[$i]
		If $line <> "" Then
			$name = StringTrimLeft($line, StringInStr($line,"(report note):")+14)
			$initer = StringMid($name,StringInStr($name,":")+1,-1)

			If StringInStr($initer,"''") <> 0 Then
				$initerR = ""
				For $j = 1 To StringLen($initer)
					$c = StringMid($initer,$j,1)
					If $c = "1" or $c = "0" or $c = "U" Then $initerR = $c&$initerR
				Next
				$initer = '"'&$initerR&'"'
			EndIf

			$name = StringMid($name,1,StringInStr($name,":")) & $initer
			If StringMid($name,1,3) = "OUT" Then
				$signals &= StringTrimLeft($name,4) & "|"
			Else
				$outputs &= $name & "|"
			EndIf
		EndIf
	Next
	If $outputs <> "" Then $outputs = StringTrimRight($outputs, 1)
	If $signals <> "" Then $signals = StringTrimRight($signals, 1)

	Return $outputs&"{}"&$signals
EndFunc

Func ghdl($action, $file, $failsafe = True, $track = false)
	If $failsafe Then
		$failsafe = "--ieee=synopsys -fexplicit"
	Else
		$failsafe = ""
	EndIf

	Return _RunWaitGet(@ComSpec & " /c "&@ScriptDir&"\GHDL\bin\ghdl.exe "&$action&" "&$failsafe&" "&$file,$track?1:2,"",@SW_HIDE)
EndFunc