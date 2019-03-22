#Region ;**** Directives created by AutoIt3Wrapper_GUI ****
#AutoIt3Wrapper_Outfile=..\HardScratch\HardScratch\src\res\vmess\vmess.exe
#AutoIt3Wrapper_Res_Description=VME Simulated Simulation
#AutoIt3Wrapper_Res_Fileversion=1.0.0.9
#AutoIt3Wrapper_Res_Fileversion_AutoIncrement=y
#AutoIt3Wrapper_Res_ProductName=VMESS
#AutoIt3Wrapper_Res_ProductVersion=0.9
#AutoIt3Wrapper_Res_CompanyName=Liveployers
#AutoIt3Wrapper_Res_LegalCopyright=B0vE
#AutoIt3Wrapper_Res_LegalTradeMarks=Borja Live
#EndRegion ;**** Directives created by AutoIt3Wrapper_GUI ****
#include "_RunWaitGet.au3"
#include "VME2.au3"
;	VMESS:   VME SIMULATED SIMULATION

;Esto hay que eliminarlo
;$cmdLine = __getArray()
;__add($cmdLine, "sim")
;__add($cmdLine, "C:\Users\Arlin-T2\AppData\Roaming\HardScratch\FAIL_test")
;__add($cmdLine, "entradaR:'1'|null")

$path = StringReplace(__getArgument(2)," ","_")
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
		__Error(0,0)
	case "sim"
		_loadIDLINES($pathSim&"\IDLINES.B0vE")
		;MsgBox(0,"RECIBIDO",__getArgument(3))
		$response = _modifyModels($pathSim, __getArgument(3))
		;MsgBox(0,"",$path)
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

Func _modifyModels($path, $text); Init True: No se conocen las señales, hay que leerlas del compilador $VARIABLES
	$file = $path&"\sim.vhdl"
	$fileTest = $path&"\simTest.vhdl"
	$model = $path&"\base.vhdl"
	$modelTest = $path&"\baseTest.vhdl"
	If FileExists($file) Then FileDelete($file)
	If FileExists($fileTest) Then FileDelete($fileTest)

	$text = StringReplace($text,"null","",0,2)
	while StringMid($text,StringLen($text),1) = "|"
		$text = StringTrimRight($text,1)
	WEnd

	$inits = __getArray()
	$parts = StringSplit($text,"|")
	For $i = 1 To $parts[0]
		If StringInStr($parts[$i], ":") = "" Then ContinueLoop
		$subParts = StringSplit($parts[$i],":")
		$subParts[2] = __correctComas($subParts[2])
		$init = __getArray()
		__add($init,$subParts[1])
		__add($init,$subParts[2])
		__add($inits,$init)
	Next


	$Text_file = FileRead($model)
	$Text_fileTest = FileRead($modelTest)
	For $i = 1 To $inits[0]
		$init = $inits[$i]
		$Text_file = StringReplace($Text_file, "<"&$init[1]&">", ":= "&$init[2])
		$Text_fileTest = StringReplace($Text_fileTest, "<"&$init[1]&">", ":="&$init[2])
	Next

	;Generar ID de simulacion, para que GHDL no diga cosas sobre entidades duplicadas
	$randomName = __davinciRandomGenerator(6)
	$Text_file = StringReplace($Text_file,"<*Rname>",$randomName)
	$Text_fileTest = StringReplace($Text_fileTest,"<*Rname>",$randomName)

	$Text_file = _deleteMarks($Text_file)
	$Text_fileTest = _deleteMarks($Text_fileTest)

	FileWrite($file,$Text_file)
	FileWrite($fileTest,$Text_fileTest)

	ghdl("--clean", "")
	ghdl("-a", $file)
	ghdl("-a", $fileTest)
	Return ghdl("-r","HardScratchSimulation_Entity_"&$randomName, True, True)
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
				If $distance >= 12 Then ;Los 9 caracteres + 4 que ocupan las OUT_
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
	;Sanitizar el texto
	$text = StringMid($text,StringInStr($text,"STARTHERE"))
	$text = StringReplace($text, "STARTHERE", "")

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
	If $outputs = "" Then
		$outputs = "-1"
	Else
		$outputs = StringTrimRight($outputs, 1)
	EndIf
	If $signals = "" Then
		$signals = "-1"
	Else
		$signals = StringTrimRight($signals, 1)
	EndIf

	Return "[]"&$outputs&"{}"&$signals
EndFunc

Func __correctComas($text)
	If StringLen($text) > 3 And StringMid($text,1,1) = "'" And StringMid($text,StringLen($text),1) = "'" Then Return StringReplace($text,"'",'"')
	return $text
EndFunc
Func __davinciRandomGenerator($digits)
	$pwd = ""	;Cambiar un poco esto
	Local $aSpace[3]
	For $i = 1 To $digits
		$aSpace[0] = Chr(Random(65, 90, 1)) ;A-Z
		$aSpace[1] = Chr(Random(97, 122, 1)) ;a-z
		$aSpace[2] = Chr(Random(48, 57, 1)) ;0-9
		$pwd &= $aSpace[Random(0, 2, 1)]
	Next
	return $pwd
EndFunc
Func ghdl($action, $file, $failsafe = True, $track = false)
	If $failsafe Then
		$failsafe = "--ieee=synopsys -fexplicit"
	Else
		$failsafe = ""
	EndIf

	Return __runWaitGet(@ComSpec&" /c "&@ScriptDir&"\GHDL\bin\ghdl.exe "&$action&" "&$failsafe&" "&$file, false)
EndFunc