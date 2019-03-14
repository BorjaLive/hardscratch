#include "_BUMLAY.au3"

#Region ;Constantes y codigos de error
Const $ERROR[] = ["BOKEY", "CANNOT_INICIALIZE_INOUT", "CONST_MUST_BE_INICIALIZED", "OUT_VAR_CANNOT_BE_READEN", "CONST_VAR_CANNOT_BE_ASIGNED", _
"CONVERSION_NOT_ALLOWED", "IN_VAR_CANNOT_BE_ASIGNED", "SETIF_IS_USELESS", "SETSWITCH_IS_USELESS", "ELSE_VALUE_NEEDED", "SWITCH_NEEDS_DEFAULT_CASE", _
"VARIABLE_DOES_NOT_EXIST"]
Const 	$ERROR_BOKEY = 0, _
		$ERROR_CANNOT_INICIALIZE_INOUT = 1, _
		$ERROR_CONST_MUST_BE_INICIALIZED = 2, _
		$ERROR_OUT_VAR_CANNOT_BE_READEN = 3, _
		$ERROR_CONST_VAR_CANNOT_BE_ASIGNED = 4, _
		$ERROR_CONVERSION_NOT_ALLOWED = 5, _
		$ERROR_IN_VAR_CANNOT_BE_ASIGNED = 6, _
		$ERROR_SETIF_IS_USELESS = 7, _
		$ERROR_SETSWITCH_IS_USELESS = 8, _
		$ERROR_ELSE_VALUE_NEEDED = 9, _
		$ERROR_SWITCH_NEEDS_DEFAULT_CASE = 10, _
		$ERROR_VARIABLE_DOES_NOT_EXIST = 11

Global $PROCESS_USED_VARS = Null, $CONSTRUCTOR_USED_VARS= Null, $VARIABLES = Null
#EndRegion

#Region ;Rutina principal
;Esto hay que eliminarlo
$cmdLine = __getArrayInicialzer("C:\Users\Arlin-T2\AppData\Roaming\HardScratch\test")
compile()


Func compile()
	$file = __getArgument(1)&"\circuit.b0ve"
	$raw = __BUMLAY_load($file)
	;_ArrayDisplay($raw)

	$VARIABLES = loadVars($raw)


	$lines = __getArray()
	__add($lines, generateHeader(), true)
	__add($lines, generateEntity(), true)
	__add($lines, generateArchitecture($raw), true)



	;_ArrayDisplay($lines)
	__write($lines,__getArgument(1)&"\code.vhdl")
EndFunc
#EndRegion

#Region ;Compilacion
Func loadVars($data)
	$vars = __getArray()

	For $i = 1 To $data[0]
		$tmp = $data[$i]
		If $tmp[1] = "VAR" Then
			$var = __getArray()
			__add($var, $tmp[2])
			__add($var, $tmp[3])
			__add($var, $tmp[4])
			__add($var, $tmp[5])
			__add($var, $tmp[6])
			__add($var, $tmp[7])

			If $var[4] = 3 And ($var[5] = "-1" or $var[5] = "") Then $var[5] = "8"
			__add($vars,$var)
		EndIf
	Next

	return $vars
EndFunc

Func generateHeader()
	$lineas = __getArray()

	__add($lineas, "----------------------------------------------------------------------------------" & @CRLF & _
	"-- Enginer:        " & @UserName & @CRLF & _
	"-- Create Date:    " & @HOUR & ":" & @MIN & ":" & @SEC & " " & @MDAY & "/" & @MON & "/" & @YEAR & @CRLF & _
	"-- Module Name:    " & @CRLF & _
	"-- Project Name:   "& @CRLF & _
	"-- Description:    Generated with VME2 and HardScratch"& @CRLF & _
	"--"& @CRLF & _
	"-- VME2 is an adaptation of the currently unsuported VME. "&@CRLF&"-- HardScratch is a Scratch-like visual interface to generate VHDL code."& @CRLF & _
	"----------------------------------------------------------------------------------" & @CRLF & _
	"library IEEE;" & @CRLF & _
	"use IEEE.STD_LOGIC_1164.ALL;" & @CRLF & _
	"use IEEE.STD_LOGIC_ARITH.ALL;" & @CRLF & _
	"use IEEE.STD_LOGIC_UNSIGNED.ALL;" & @CRLF & @CRLF _
	)

	Return $lineas
EndFunc
Func generateEntity()
	$lineas = __getArray()

	__add($lineas, "Entity HardScratch_Entity is" & @CRLF & @TAB & "Port(")

	For $i = 1 To $VARIABLES[0]
		$var = $VARIABLES[$i]
		if $var[3] = 1 Or $var[3] = 2 Then __add($lineas, @TAB&@TAB&_variableConstructor($var) & ($i<>$VARIABLES[0]?";":(@CRLF&@TAB&");")))
	Next

	__add($lineas, "End HardScratch_Entity;"&@CRLF)

	Return $lineas
EndFunc
Func generateArchitecture($raw)
	$lines = __getArray()
	__add($lines,"Architecture HardScratch_Architecture of HardScratch_Entity is")

	;VARS
	For $i = 1 To $VARIABLES[0]
		$var = $VARIABLES[$i]
		if $var[3] = 3 Or $var[3] = 4 Then __add($lines, @TAB&_variableConstructor($var) & ";")
	Next

	__add($lines,"Begin")

	;LOGIC
	For $i = 1 To $raw[0]
		$logic = $raw[$i]
		if $logic[1] <> "VAR" Then __add($lines, _logicConstructor($logic), true)
	Next

	__add($lines, "End HardScratch_Architecture;")
	return $lines
EndFunc

Func _variableConstructor($var)
	; [0. 6],[1. ID],[2. Nombre],[3. IO],[4. Tipo],[5. Atributos],[6. inicializacion]
	$mod = ""
	Switch $var[3]
		Case 3
			$mod = "Signal "
		Case 4
			$mod = "Constant "
			If $var[6] = "-1" Then __Error($ERROR_CONST_MUST_BE_INICIALIZED, $var[1])
		Case Else
			If $var[6] <> "-1" Then __Error($ERROR_CANNOT_INICIALIZE_INOUT,$var[1])
	EndSwitch

	$io = ""
	Switch $var[3]
		Case 1
			$io = "In "
		Case 2
			$io = "Out "
	EndSwitch

	$type = ""
	Switch $var[4]
		Case 1
			$type = "INTEGER range 255 downto 0"
		Case 2
			$type = "STD_LOGIC"
		Case 3
			$type = "STD_LOGIC_VECTOR("&$var[5]&" downto 0)"
	EndSwitch

	$init = _decodeConstructor($var[6], $var[1])
	If $init <> "" Then $init = " := "&$init

	return $mod & $var[2] &" : "& $io & $type & $init
EndFunc
Func _decodeConstructor($data, $id)
	If $data = "-1" Then Return ""
	$text = ""
	$CONSTRUCTOR_USED_VARS = __getArray()

	$parts = StringSplit($data,"-")
	For $i = 1 To $parts[0]
		$part = $parts[$i]
		If StringMid($part,1,1) = "L" Then
			$type = StringMid($part,2,1)
			$literal = StringTrimLeft($part,3)
			If $type = "N" Then
				$text &= $literal
			ElseIf $type = "B" Then
				$text &= "'"&$literal&"'"
			ElseIf $type = "A" Then
				$text &= '"'&$literal&'"'
			EndIf
			$text&=" "
		ElseIf StringMid($part,1,2) = "SA" Then
			$subParts = StringSplit($part,":")
			$var = getVarByName($subParts[3], $id)
			_varCheckRead($var, $id)
			__add($CONSTRUCTOR_USED_VARS,$var[2])
			$text &= $var[2]&"("&$subParts[2]&") "
		ElseIf StringMid($part,1,2) = "CK" Then
			$var = getVarByName(StringTrimLeft($part,3), $id)
			_varCheckRead($var, $id)
			__add($CONSTRUCTOR_USED_VARS,$var[2])
			$text &= "("& $var[2] &"'event and "& $var[2] &"='1' ) "
		Else
			$text &= StringReplace($part,"_","-")&" "
			;Tiene que reconocer variables y guardarlas en otra pila global
			If Not _isOperator($part) Then
				$var = getVarByName($part, $id)
				_varCheckRead($var, $id)
				__add($CONSTRUCTOR_USED_VARS,$var[2])
			EndIf
		EndIf
	Next

	return StringTrimRight($text,1)
EndFunc
Func _logicConstructor($logic)
	;LOGIC CONSTANTS [0. ?],[1. Identifier],[2. ID]...
	;VAR [0. 6],[1. ID],[2. Nombre],[3. IO],[4. Tipo],[5. Atributos],[6. inicializacion]
	If Not IsArray($logic) Then Return __getArray()


	$lines = __getArray()
	If $logic[1] = "CONV" Then
		;CONVERTER [0. 4],[1. CONV],[2. ID],[3. VAR1],[4. VAR2]
		$var1 = getVarByName($logic[3], $logic[2])
		$var2 = getVarByName($logic[4], $logic[2])

		_varCheckRead($var2,$logic[2])
		_varCheckWrite($var1,$logic[2])

		If $var1[4] = 1 And $var2[4] = 3 Then
			$conv = "CONV_INTEGER("&$var2[2]
		ElseIf $var1[4] = 3 And $var2[4] = 1 Then
			$conv = "CONV_STD_LOGIC_VECTOR("&$var2[2]&", "&$var1[5]
		Else
			__Error($ERROR_CONVERSION_NOT_ALLOWED,$logic[2])
		EndIf

		__addIndentation($lines, $var1[2] &" <= "& $conv &");")
	ElseIf $logic[1] = "ASIG" Then
		;CONVERTER [0. 4],[1. ASIG],[2. ID],[3. VAR],[4. Expresion A]
		$var = getVarByName($logic[3], $logic[2])

		_varCheckWrite($var,$logic[2])

		__addIndentation($lines, $var[2] &" <= "& _decodeConstructor($logic[4], $logic[2]) &";")
	ElseIf $logic[1] = "SETIF" Then
		;SETIF [0. 3+2n],[1. SETIF],[2. ID],[3. VAR],[4 + 2n. Condition],[5 + 2n. Value]
		$var = getVarByName($logic[3], $logic[2])

		_varCheckWrite($var,$logic[2])

		If $logic[0] < 6 Then __Error($ERROR_SETIF_IS_USELESS, $logic[2])
		If $logic[5] = "-1" Then __Error($ERROR_ELSE_VALUE_NEEDED, $logic[2])
		$indentation = _genIndentation(StringLen($var[2])+4)

		While $logic[$logic[0]] = "-1"
			__deleteElement($logic,$logic[0])
		WEnd

		__addIndentation($lines, $var[2] &" <= "& _decodeConstructor($logic[7], $logic[2]) &" When "& _decodeConstructor($logic[6], $logic[2]) &" Else")
		For $i = 8 To $logic[0]-1 Step 2
			If $logic[$i+1] <> "-1" And $logic[$i] <> "-1" Then
				__addIndentation($lines, $indentation& _decodeConstructor($logic[$i+1], $logic[2]) &" When "& _decodeConstructor($logic[$i], $logic[2]) &" Else")
			EndIf
		Next

		__addIndentation($lines, $indentation& _decodeConstructor($logic[5], $logic[2]) &";")
	ElseIf $logic[1] = "SETSWITCH" Then
		;SETSWITCH [0. 4+2n],[1. SETIF],[2. ID],[3. VAR1],[4. VAR2],[5 + 2n. Condition],[6 + 2n. Value]
		$var1 = getVarByName($logic[3], $logic[2])
		$var2 = getVarByName($logic[4], $logic[2])

		_varCheckWrite($var1,$logic[2])
		_varCheckRead($var2,$logic[2])

		If $logic[0] < 7 Then __Error($ERROR_SETSWITCH_IS_USELESS, $logic[2])
		If $logic[6] = "-1" Then __Error($ERROR_ELSE_VALUE_NEEDED, $logic[2])
		$indentation = _genIndentation(StringLen($var1[2])+4)

		While $logic[$logic[0]] = "-1"
			__deleteElement($logic,$logic[0])
		WEnd

		__addIndentation($lines,"WITH "& $var2[2] &" SELECT")
		__addIndentation($lines, $var1[2] &" <= "& _decodeConstructor($logic[8], $logic[2]) &" When "& _decodeConstructor($logic[7], $logic[2]) &",")
		For $i = 9 To $logic[0]-1 Step 2
			If $logic[$i+1] <> "-1" And $logic[$i] <> "-1" Then
				__addIndentation($lines, $indentation& _decodeConstructor($logic[$i+1], $logic[2]) &" When "& _decodeConstructor($logic[$i], $logic[2]) &",")
			EndIf
		Next

		__addIndentation($lines, $indentation& _decodeConstructor($logic[6], $logic[2]) &";")
	ElseIf $logic[1] = "SEQ" Then
		;SEQ [0. 3+n],[1. SEQ],[2. ID],[3+n. INSTRUCTIONS]
		$instructions = __getArray()
		$PROCESS_USED_VARS = __getArray()
		$usedVarsList = __getArray()

		If $logic[0] < 3 Then Return $lines

		For $i = 3 To $logic[0]
			__add($instructions, _logicConstructor($logic[$i]), True)
			__add($usedVarsList,$PROCESS_USED_VARS, true)
		Next

		$usedVarsList = _depurateProcessUsedVars($usedVarsList)
		If $usedVarsList[0] > 0 Then
			$usedVars = ""
			For $i = 1 To $usedVarsList[0]
				$usedVars &= $usedVarsList[$i]&", "
			Next
			$usedVars = StringTrimRight($usedVars,2)
			__addIndentation($lines, "Process("& $usedVars &")")
		Else
			__addIndentation($lines, "Process")
		EndIf
		__addIndentation($lines, "Begin")
		__addIndentation($lines, $instructions)
		__addIndentation($lines, "End Process;")

	ElseIf $logic[1] = "SASIG" Then
		;SASIG [0. 4],[1. SEQ],[2. ID],[3. VAR],[4. Value]
		$var = getVarByName($logic[3], $logic[2])
		$usedVars = __getArray()
		__add($usedVars, $var[2])

		_varCheckWrite($var,$logic[2])
		__addIndentation($lines, $var[2] &" <= "& _decodeConstructor($logic[4], $logic[2]) &";")
		__add($usedVars, $CONSTRUCTOR_USED_VARS, True)

		$PROCESS_USED_VARS = __getArray()
		__add($PROCESS_USED_VARS, $usedVars, True)
	ElseIf $logic[1] = "IFTHEN" Then
		;IFTHEN [0. 4+2n],[1. IFTHEN],[2. ID],[3+n. Condition],[4+n. SEQ Instructions]
		If $logic[0] < 5 Then Return $lines
		$usedVars = __getArray()

		$starter = "If "
		For $i = 5 To $logic[0]-1 Step 2
			If $logic[$i] = "-1" Or $logic[$i+1] = "-1" Then ContinueLoop

			$condition = _decodeConstructor($logic[$i], $logic[2])
			__add($usedVars, $CONSTRUCTOR_USED_VARS, True)
			$actions = _logicConstructor($logic[$i+1])
			__add($usedVars, $PROCESS_USED_VARS, True)

			__addIndentation($lines, $starter & $condition &" Then")
			__addIndentation($lines, $actions)
			$starter = "ElsIf "
		Next
		If $logic[4] <> "-1" Then
			__addIndentation($lines, "Else")
			__addIndentation($lines, _logicConstructor($logic[4]))
			__add($usedVars, $PROCESS_USED_VARS, True)
		EndIf
		__addIndentation($lines, "End If;")

		$PROCESS_USED_VARS = __getArray()
		__add($PROCESS_USED_VARS, $usedVars, True)
	ElseIf $logic[1] = "SWITCH" Then
		;SWITCH [0. 5+2n],[1. SWITCH],[2. ID],[3. Variable],[4+n. Condition],[5+n. SEQ Instructions]
		If $logic[0] < 6 Then Return $lines
		$var = getVarByName($logic[3], $logic[2])
		_varCheckRead($var, $logic[2])
		If $logic[5] = "-1" Then __Error($ERROR_SWITCH_NEEDS_DEFAULT_CASE,$logic[2])
		$usedVars = __getArray()

		__addIndentation($lines, "Case "& $var[2] &" is")
		For $i = 6 To $logic[0]-1 Step 2
			If $logic[$i] = "-1" Or $logic[$i+1] = "-1" Then ContinueLoop

			__addIndentation($lines, @TAB&"When "&_decodeConstructor($logic[$i], $logic[2])&" => ")
			__add($usedVars, $CONSTRUCTOR_USED_VARS, True)
			__addIndentationX2($lines, _logicConstructor($logic[$i+1]))
			__add($usedVars, $PROCESS_USED_VARS, True)
		Next
		__addIndentation($lines, @TAB&"When Others => ")
		__addIndentationX2($lines, _logicConstructor($logic[5]))
		__add($usedVars, $PROCESS_USED_VARS, True)
		__addIndentation($lines, "End Case;")


		$PROCESS_USED_VARS = __getArray()
		__add($PROCESS_USED_VARS,$var[2])
		__add($PROCESS_USED_VARS, $usedVars, True)
	ElseIf $logic[1] = "WFOR" Then
		;WAITFOR [0. 3],[1. WFOR],[2. ID],[3. Time]
		__addIndentation($lines, "Wait for "&_decodeConstructor($logic[3], $logic[2])&";")
		$PROCESS_USED_VARS = __getArray()
		__add($PROCESS_USED_VARS, $CONSTRUCTOR_USED_VARS, True)
	ElseIf $logic[1] = "WON" Then
		;WAITON [0. 4],[1. WON],[2. ID],[3. Var],[4. Edge]
		$var = getVarByName($logic[3], $logic[2])
		_varCheckRead($var, $logic[2])

		$event = "ERROR"
		If $logic[4] = 1 Then
			$event = "'1'"
		ElseIf $logic[4] = 2 Then
			$event = "'0'"
		EndIf
		__addIndentation($lines, "Wait on "&$var[2]&" until "&$var[2]&" = "&$event&";")
		$PROCESS_USED_VARS = __getArray()
		__add($PROCESS_USED_VARS, $var[2])
	EndIf
		;FORNEXT is in progress

	Return $lines
EndFunc
#EndRegion

#Region Internal UDF
Func __getArgument($n)
	If $n > $cmdLine[0] Then return ""
	Return $cmdLine[$n]
EndFunc
Func __Error($code, $id)
	MsgBox(0,"ERROR","ERROR "&$code&" "&$ERROR[$code]&" with ID "&$id)
	ConsoleWrite($code)
	Exit
EndFunc
Func __addIndentation(ByRef $array, $elements)
	If IsArray($elements) Then
		For $i = 1 To $elements[0]
			__add($array, @TAB&$elements[$i])
		Next
	Else
		__add($array, @TAB&$elements)
	EndIf
EndFunc
Func __addIndentationX2(ByRef $array, $elements)
	If IsArray($elements) Then
		For $i = 1 To $elements[0]
			__add($array, @TAB&@TAB&$elements[$i])
		Next
	Else
		__add($array, @TAB&@TAB&$elements)
	EndIf
EndFunc
Func _varCheckWrite($var, $id)
	If $var[3] = 4 Then __Error($ERROR_CONST_VAR_CANNOT_BE_ASIGNED,$id)
	If $var[3] = 1 Then __Error($ERROR_IN_VAR_CANNOT_BE_ASIGNED,$id)
EndFunc
Func _varCheckRead($var, $id)
	If $var[3] = 2 Then __Error($ERROR_OUT_VAR_CANNOT_BE_READEN,$id)
EndFunc
Func _genIndentation($n)
	$text = ""

	For $i = 1 To $n
		$text &= " "
	Next

	Return $text
EndFunc
Func _depurateProcessUsedVars($array)
	If Not IsArray($array) Then Return

	$i = 1
	While $i <= $array[0]
		$var = getVarByName($array[$i], -1)
		If IsArray($var) And ($var[3] = 2 Or $var[3] = 4) Then
			__deleteElement($array,$i)
		Else
			$i += 1
		EndIf
	WEnd

	__purgeRepetitions($array)

	return $array
EndFunc
Func _isOperator($text)
	$text = StringReplace($text," ","")
	Return $text="+" Or $text="-" Or $text="*" Or $text="/" Or $text="(" Or $text=")" Or $text="&" Or $text="=" Or $text="AND" Or $text="OR" Or _
			 $text="XOR" Or $text="NAND" Or $text="NOR" Or $text="XNOR" Or $text="NOT" Or $text=""
EndFunc

Func getVarByName($name, $id)
	For $i = 1 To $VARIABLES[0]
		$var = $VARIABLES[$i]
		If $var[2] = $name Then Return $var
	Next
	If _isOperator($name) Then Return -1
	__Error($ERROR_VARIABLE_DOES_NOT_EXIST, $id)
EndFunc

Func __write($lines, $file)
	$text = ""

	For $i = 1 To $lines[0]
		$text &= $lines[$i]&@CRLF
	Next

	If FileExists($file) Then FileDelete($file)
	FileWrite($file,$text)
EndFunc
Func _FUBAR()

EndFunc

#EndRegion