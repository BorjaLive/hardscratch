#Region ;**** Directives created by AutoIt3Wrapper_GUI ****
#AutoIt3Wrapper_Outfile=..\..\src\res\vmess\vmess.exe
#EndRegion ;**** Directives created by AutoIt3Wrapper_GUI ****
#Region ;**** Directives created by AutoIt3Wrapper_GUI ****
#AutoIt3Wrapper_Outfile=..\HardScratch\HardScratch\src\res\vmess\vmess.exe
#EndRegion ;**** Directives created by AutoIt3Wrapper_GUI ****
#include "_BUMLAY.au3"

#Region ;Constantes y codigos de error
Const $ERROR[] = ["BOKEY", "CANNOT_INICIALIZE_INOUT", "CONST_MUST_BE_INICIALIZED", "OUT_VAR_CANNOT_BE_READEN", "CONST_VAR_CANNOT_BE_ASIGNED", _
"CONVERSION_NOT_ALLOWED", "IN_VAR_CANNOT_BE_ASIGNED", "SETIF_IS_USELESS", "SETSWITCH_IS_USELESS", "ELSE_VALUE_NEEDED", "SWITCH_NEEDS_DEFAULT_CASE", _
"VARIABLE_DOES_NOT_EXIST", "ILEGAL_USE_OF_OPERATOR_ADD", "ILEGAL_USE_OF_OPERATOR_SUB", "ILEGAL_USE_OF_OPERATOR_TIM", "ILEGAL_USE_OF_OPERATOR_TAK", _
"ILEGAL_USE_OF_OPERATOR_CONCATENATE", "BAD_INICIALIZATION", "CANT_CHANGE_LENGTH_OF_BITARRAY","PROYECT_IS_EMPTY", "PROBLEM_LOADING", "BAD_CONDITION", "BAD_EXPRESSION"]
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
		$ERROR_VARIABLE_DOES_NOT_EXIST = 11, _
		$ERROR_ILEGAL_USE_OF_OPERATOR_ADD = 12, _
		$ERROR_ILEGAL_USE_OF_OPERATOR_SUB = 13, _
		$ERROR_ILEGAL_USE_OF_OPERATOR_TIM = 14, _
		$ERROR_ILEGAL_USE_OF_OPERATOR_TAK = 15, _
		$ERROR_ILEGAL_USE_OF_OPERATOR_CONCATENATE = 16, _
		$ERROR_BAD_INICIALIZATION = 17, _
		$ERROR_CANT_CHANGE_LENGTH_OF_BITARRAY = 18, _
		$ERROR_PROYECT_IS_EMPTY = 19, _
		$ERROR_PROBLEM_LOADING = 20, _
		$ERROR_BAD_CONDITION = 21, _
		$ERROR_BAD_EXPRESSION = 22

Global $PROCESS_USED_VARS = Null, $CONSTRUCTOR_USED_VARS= Null, $VARIABLES = Null, $IDLINES = Null, $TMP_IDLINES = Null
Global $SIMULATION_MODE = Null, $SIM_PROCESS_COUNTER = Null
#EndRegion

;compile("C:\Users\Arlin-T2\AppData\Roaming\HardScratch\test\circuit.b0ve", "C:\Users\Arlin-T2\AppData\Roaming\HardScratch\test\code.vhdl")

Func compile($file, $output, $simulation = False)	;Simylation True: Crear salidas para cada señal y asignarlas. Dejar <VAR_NAME> para remplazar la incializacion de las señales Remplazar por ":= FOO". Y ejecuta CreateTest
	$SIMULATION_MODE = $simulation

	$raw = __BUMLAY_load($file)
	;_ArrayDisplay($raw)
	If Not IsArray($raw) or $raw[0] = 0 Then __Error($ERROR_PROYECT_IS_EMPTY, -1)

	$VARIABLES = loadVars($raw)
	$IDLINES = __getArray()

	$lines = __getArray()
	__add($lines, generateHeader(), True)
	__add($lines, generateEntity(), True)
	__add($lines, generateArchitecture($raw), True)


	;_ArrayDisplay($lines)
	__write($lines, $output)

	If $simulation Then createTest(StringReplace($output,".vhdl","Test.vhdl",0,2))
	If $simulation Then createIDLINES(StringMid($output,1,StringInStr($output,"\",0,-1))&"IDLINES.B0vE")
EndFunc
Func createTest($output)
	$lines = __getArray()

	;HEADER
	__add($lines, "----------------------------------------------------------------------------------" & @CRLF & _
	"-- Enginer:        " & @UserName & @CRLF & _
	"-- Create Date:    " & @HOUR & ":" & @MIN & ":" & @SEC & " " & @MDAY & "/" & @MON & "/" & @YEAR & @CRLF & _
	"-- Module Name:    " & @CRLF & _
	"-- Project Name:   "& @CRLF & _
	"-- Description:    Generated with VMESS and HardScratch"& @CRLF & _
	"--"& @CRLF & _
	"-- VMESS is a component of VME2, witch is used by HardScratch to simulate autogenerated VHDL code. "&@CRLF&"-- HardScratch is a Scratch-like visual interface to generate VHDL code."& @CRLF & _
	"----------------------------------------------------------------------------------" & @CRLF & _
	"library IEEE;" & @CRLF & _
	"use IEEE.STD_LOGIC_1164.ALL;" & @CRLF & _
	"use IEEE.STD_LOGIC_ARITH.ALL;" & @CRLF & _
	"use IEEE.STD_LOGIC_UNSIGNED.ALL;" & @CRLF & @CRLF _
	)

	;ENTITY
	__add($lines, "ENTITY HardScratchSimulation_Entity_<*Rname> IS"&@CRLF&"END HardScratchSimulation_Entity_<*Rname>;"&@CRLF)

	;ARCHITECTURE
	__add($lines, "ARCHITECTURE HardScratchSimulation_Architecture_<*Rname> OF HardScratchSimulation_Entity_<*Rname> IS" & @CRLF & @CRLF & _
	@TAB&"-- Componente bajo prueba (UUT)" & @CRLF & @CRLF & _
	@TAB&"COMPONENT HardScratch_Entity_<*Rname>" & @CRLF & _
	@TAB&"PORT(" & @CRLF)

	$filtered = __getArray()
	For $i = 1 To $VARIABLES[0]
		$var = $VARIABLES[$i]
		If $var[3] = 1 Or $var[3] = 2 Or $var[3] = 3 Then __add($filtered,$var)
	Next

	For $i = 1 To $filtered[0]
		$var = $filtered[$i]
		$out = ";"
		If $i = $filtered[0] Then $out = @CRLF&@TAB&");"

		If $var[3] = 3 Then
			$var[3] = 2
			$var[2] = "OUT_"&$var[2]
			$var[6] = "-1"
		EndIf
		__add($lines,@TAB&@TAB&_variableConstructor($var) & $out)
	Next

	__add($lines, @TAB&"END COMPONENT;"&@CRLF&@CRLF&@TAB&"--Inputs")

	For $i = 1 To $filtered[0]
		$var = $filtered[$i]
		If $var[3] = 1 Then __add($lines,@TAB&"Signal "&StringReplace(_variableConstructor($var)," In "," ",-1,2) &";")
	Next
	__add($lines,@CRLF&@TAB&"--Outputs")
	For $i = 1 To $filtered[0]
		$var = _simulateVar($filtered[$i])
		If $var[3] = 2  Then __add($lines,@TAB&"Signal "&StringReplace(_variableConstructor($var)," Out "," ",-1,2) &";")
	Next

	__add($lines, @CRLF&"BEGIN"&@CRLF&@TAB&"uut: HardScratch_Entity_<*Rname> PORT MAP (")

	For $i = 1 To $filtered[0]
		$var = _simulateVar($filtered[$i])
		$out = ","
		If $i = $filtered[0] Then $out = @CRLF&@TAB&");"

		__add($lines,@TAB&@TAB&$var[2]&" => "&$var[2] &$out)
	Next

	__add($lines, @CRLF&@TAB&"--Ejecucion"&@CRLF&@TAB&"process"&@CRLF&@TAB&"begin"&@CRLF&@TAB&@TAB&"wait for 100 ns;"&@CRLF&@TAB&@TAB&'report "STARTHERE";'&@CRLF)


	For $i = 1 To $filtered[0]
		$var = _simulateVar($filtered[$i])
		If $var[3] <> 2 Then ContinueLoop

		Switch($var[4])
			Case 1
				;Es un Integer
				__add($lines, @TAB&@TAB& 'report "'& $var[2] &':"&'&"integer'image("& $var[2] &");")
			Case 2
				;Es un bit
				__add($lines, @TAB&@TAB& 'report "'& $var[2] &':"&'&"std_logic'image("& $var[2] &");")
			Case 3
				;Es un array
				$text = @TAB&@TAB& 'report "' & $var[2] &':"'
				For $j = 0 To $var[5]
					$text &= " & std_logic'image("& $var[2] &"("& $j &"))"
				Next
				__add($lines,$text&";")
		EndSwitch
	Next

	For $i = 1 To $VARIABLES[0]
		$var = $VARIABLES[$i]
		If $var[3] = 1 And $var[4] = 2 And ($var[2]="CK" Or $var[2]="CLK" Or $var[2]="CLOCK") Then
			__add($lines, "If "&$var[2]&" = '1' Then "&$var[2]&" <= '0'; End If;")
		EndIf
	Next

	__add($lines, @CRLF&@TAB&@TAB&"wait;"&@CRLF&@TAB&"end process;"&@CRLF&"END;")

	__write($lines, $output)
EndFunc
Func createIDLINES($output)
	$lines = __getArray()

	For $i = 1 To $IDLINES[0]
		__add($lines, $IDLINES[$i]& ($i = $IDLINES[0]?"":"|"))
	Next

	__write($lines, $output)
EndFunc

#Region ;Compilacion
Func loadVars($data)
	$vars = __getArray()

	For $i = 1 To $data[0]
		$tmp = $data[$i]
		If $tmp[1] = "VAR" Then
			$var = __getArray()
			If $tmp[6] <> -1 Then $tmp[6] -= 1
			__add($var, $tmp[2])
			__add($var, $tmp[3])
			__add($var, $tmp[4])
			__add($var, $tmp[5])
			__add($var, $tmp[6])
			__add($var, $tmp[7])
			__add($var, $tmp[1])

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

	_idlinesNull(16)
	Return $lineas
EndFunc
Func generateEntity()
	$lineas = __getArray()

	$rName = ""
	If $SIMULATION_MODE Then $rName = "_<*Rname>"

	__add($lineas, "Entity HardScratch_Entity"&$rName&" is" & @CRLF & @TAB & "Port(")
	_idlinesNull(2)

	$filtered = __getArray()
	For $i = 1 To $VARIABLES[0]
		$var = $VARIABLES[$i]
		if $var[3] = 1 Or $var[3] = 2 Then __add($filtered, $var)
		;Agregar una salida por cada señal
		If $SIMULATION_MODE Then
			If $var[3] = 3 Then
				$var[2] = "OUT_"&$var[2]
				$var[3] = 2
				$var[6] = "-1"
				__add($lineas, @TAB&@TAB&_variableConstructor($var) & ";")
				__add($IDLINES, $var[1])
			EndIf
		EndIf
	Next


	For $i = 1 To $filtered[0]
		$var = $filtered[$i]
		If $var[3] = 1 Or $var[3] = 2 Then
			__add($lineas, @TAB&@TAB&_variableConstructor($var) & ($i<>$filtered[0]?";":(@CRLF&@TAB&");")))
			__add($IDLINES, $var[1])
		EndIf
	Next

	__add($lineas, "End HardScratch_Entity"&$rName&";"&@CRLF)
	_idlinesNull(3)

	Return $lineas
EndFunc
Func generateArchitecture($raw)
	$rName = ""
	If $SIMULATION_MODE Then $rName = "_<*Rname>"

	$lines = __getArray()
	__add($lines,"Architecture HardScratch_Architecture"&$rName&" of HardScratch_Entity"&$rName&" is")

	_idlinesNull(1)


	;VARS
	For $i = 1 To $VARIABLES[0]
		$var = $VARIABLES[$i]
		If $var[3] = 3 Or $var[3] = 4 Then
			__add($lines, @TAB&_variableConstructor($var) & ";")
			__add($IDLINES, $var[1])
		EndIf
	Next

	If $SIMULATION_MODE Then
		$nSeqs = 0
		For $i = 1 To $raw[0]
			$logic = $raw[$i]
			If $logic[1] = "SEQ" Then
				__add($lines, @TAB&"Signal EXECUTED_PROCESS_"&$nSeqs&" : STD_LOGIC := '0';")
				_idlinesNull(1)
				$nSeqs += 1
			EndIf
		Next
	EndIf

	__add($lines,"Begin")
	_idlinesNull(1)

	;LOGIC
	For $i = 1 To $raw[0]
		$logic = $raw[$i]
		if $logic[1] <> "VAR" Then
			__add($lines, _logicConstructor($logic), true)
			__add($IDLINES, $TMP_IDLINES, True)
		EndIf
	Next

	;agregar las asignaciones a las salidas de señales
	If $SIMULATION_MODE Then
		For $i = 1 To $VARIABLES[0]
			$var = $VARIABLES[$i]
			If $var[3] = 3 Then
				__add($lines, @TAB&"OUT_"&$var[2]&" <= "&$var[2]& ";")
				__add($IDLINES, -1)
			EndIf
		Next
	EndIf

	__add($lines, "End HardScratch_Architecture"&$rName&";")
	_idlinesNull(2)

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
			$type = "INTEGER"
		Case 2
			$type = "STD_LOGIC"
		Case 3
			$type = "STD_LOGIC_VECTOR("&$var[5]&" downto 0)"
	EndSwitch

	$init = _decodeConstructor($var[6], $var[1])
	;Agregar marcador si es para simulacion
	If $SIMULATION_MODE And $var[3] <> 4 Then
		$init = " <"&$var[2]&">"
	Else
		If $init <> "" Then $init = " := "&$init
	EndIf

	return $mod & $var[2] &" : "& $io & $type & $init
EndFunc
Func _decodeConstructor($data, $id)
	If $data = "-1" Then Return ""
	$text = ""
	$CONSTRUCTOR_USED_VARS = __getArray()

	$parts = StringSplit($data,"@")
	For $i = 1 To $parts[0]
		$part = $parts[$i]
		If StringMid($part,1,1) = "L" And StringMid($part,3,1) = ":" Then
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
		ElseIf StringMid($part,1,3) = "SA:" Then
			$subParts = StringSplit($part,":")
			$var = getVarByName($subParts[3], $id)
			_varCheckRead($var, $id)
			__add($CONSTRUCTOR_USED_VARS,$var[2])
			$text &= $var[2]&"("&$subParts[2]&") "
		ElseIf StringMid($part,1,3) = "CK:" Then
			$var = getVarByName(StringTrimLeft($part,3), $id)
			_varCheckRead($var, $id)
			__add($CONSTRUCTOR_USED_VARS,$var[2])
			If $SIMULATION_MODE Then	;Porque no hay eventos
				$text &= $var[2] &"='1' "
			Else
				$text &= "("& $var[2] &"'event and "& $var[2] &"='1') "
			EndIf
		ElseIf StringMid($part,1,3) = "CV:" Then
			$var = getVarByName(StringTrimLeft($part,3), $id)
			_varCheckRead($var, $id)
			__add($CONSTRUCTOR_USED_VARS,$var[2])
			$text &= "CONV_INTEGER("&$var[2]&") "
		ElseIf StringMid($part,1,3) = "VC:" Then
			$var = getVarByName(StringTrimLeft($part,3), $id)
			_varCheckRead($var, $id)
			__add($CONSTRUCTOR_USED_VARS,$var[2])
			$text &= "CONV_STD_LOGIC_VECTOR("&$var[2]&") "
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

		$TMP_IDLINES = __getArray()
		__add($TMP_IDLINES, $logic[2])
	ElseIf $logic[1] = "ASIG" Then
		;CONVERTER [0. 4],[1. ASIG],[2. ID],[3. VAR],[4. Expresion A]
		$var = getVarByName($logic[3], $logic[2])

		_varCheckWrite($var,$logic[2])

		__addIndentation($lines, $var[2] &" <= "& _decodeConstructor($logic[4], $logic[2]) &";")

		$TMP_IDLINES = __getArray()
		__add($TMP_IDLINES, $logic[2])
	ElseIf $logic[1] = "SETIF" Then
		;SETIF [0. 3+2n],[1. SETIF],[2. ID],[3. VAR],[4 + 2n. Condition],[5 + 2n. Value]
		$TMP_IDLINES = __getArray()
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
				__add($TMP_IDLINES, $logic[2])
			EndIf
		Next

		__addIndentation($lines, $indentation& _decodeConstructor($logic[5], $logic[2]) &";")


		__add($TMP_IDLINES, $logic[2])
		__add($TMP_IDLINES, $logic[2])
	ElseIf $logic[1] = "SETSWITCH" Then
		;SETSWITCH [0. 4+2n],[1. SETIF],[2. ID],[3. VAR1],[4. VAR2],[5 + 2n. Condition],[6 + 2n. Value]
		$TMP_IDLINES = __getArray()
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
				__add($TMP_IDLINES, $logic[2])
			EndIf
		Next

		__addIndentation($lines, $indentation& _decodeConstructor($logic[6], $logic[2]) &";")

		__add($TMP_IDLINES, $logic[2])
		__add($TMP_IDLINES, $logic[2])
		__add($TMP_IDLINES, $logic[2])
	ElseIf $logic[1] = "SEQ" Then
		;SEQ [0. 3+n],[1. SEQ],[2. ID],[3+n. INSTRUCTIONS]
		$selfIDLINES = __getArray()
		__add($selfIDLINES, $logic[2])
		__add($selfIDLINES, $logic[2])

		$instructions = __getArray()
		$PROCESS_USED_VARS = __getArray()
		$usedVarsList = __getArray()

		If $logic[0] < 3 Then Return $lines

		For $i = 3 To $logic[0]
			$constructed = _logicConstructor($logic[$i])
			if(Not IsArray($constructed) Or $constructed[0] = 0) Then ContinueLoop
			__add($instructions, $constructed, True)
			__add($usedVarsList,$PROCESS_USED_VARS, true)
			__add($selfIDLINES, $TMP_IDLINES, True)
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

		If $SIMULATION_MODE Then
			If $SIM_PROCESS_COUNTER = Null Then $SIM_PROCESS_COUNTER = 0
			__addIndentation($lines, "If EXECUTED_PROCESS_"&$SIM_PROCESS_COUNTER&" = '0' Then")
			_idlinesNull(1)
		EndIf

		__addIndentation($lines, $instructions)

		If $SIMULATION_MODE Then
			__addIndentation($lines, "EXECUTED_PROCESS_"&$SIM_PROCESS_COUNTER&" <= '1';")
			__addIndentation($lines, "End If;")
			$SIM_PROCESS_COUNTER += 1
			_idlinesNull(2)
		EndIf

		__addIndentation($lines, "End Process;")

		__add($selfIDLINES, $logic[2])
		$TMP_IDLINES = __getArray()
		__add($TMP_IDLINES, $selfIDLINES, True)
	ElseIf $logic[1] = "SASIG" Then
		;SASIG [0. 4],[1. SEQ],[2. ID],[3. VAR],[4. Value]
		$var = getVarByName($logic[3], $logic[2])
		$usedVars = __getArray()
		;__add($usedVars, $var[2])	;La variable asignada no se agrega porque la lista de sensibilidades es para cosas que cambian, escribirla no hace que quieras leerla.

		_varCheckWrite($var,$logic[2])
		__addIndentation($lines, $var[2] &" <= "& _decodeConstructor($logic[4], $logic[2]) &";")
		__add($usedVars, $CONSTRUCTOR_USED_VARS, True)

		$PROCESS_USED_VARS = __getArray()
		__add($PROCESS_USED_VARS, $usedVars, True)

		$TMP_IDLINES = __getArray()
		__add($TMP_IDLINES, $logic[2])
	ElseIf $logic[1] = "IFTHEN" Then
		;IFTHEN [0. 4+2n],[1. IFTHEN],[2. ID],[3+n. Condition],[4+n. SEQ Instructions]
		$selfIDLINES = __getArray()

		If $logic[0] < 5 Then Return $lines
		$usedVars = __getArray()

		$starter = "If "
		For $i = 5 To $logic[0]-1 Step 2
			If $logic[$i] = "-1" Or $logic[$i+1] = "-1" Then ContinueLoop

			$condition = _decodeConstructor($logic[$i], $logic[2])
			__add($usedVars, $CONSTRUCTOR_USED_VARS, True)
			$actions = __getArray()

			$innerLogic = $logic[$i+1]
			For $j = 1 To $innerLogic[0]
				__add($actions, _logicConstructor($innerLogic[$j]), True)
				__add($usedVars, $PROCESS_USED_VARS, True)
			Next
			;_ArrayDisplay($actions)

			__addIndentation($lines, $starter & $condition &" Then")
			__addIndentation($lines, $actions)
			$starter = "ElsIf "


			__add($selfIDLINES, $logic[2])
			__add($selfIDLINES, $TMP_IDLINES, True)
		Next
		If $logic[4] <> "-1" Then
			__addIndentation($lines, "Else")
			$innerLogic = $logic[4]
			For $i = 1 To $innerLogic[0]
				__addIndentation($lines, _logicConstructor($innerLogic[$i]))
				__add($usedVars, $PROCESS_USED_VARS, True)
			Next

			__add($selfIDLINES, $logic[2])
			__add($selfIDLINES, $TMP_IDLINES, True)
		EndIf
		__addIndentation($lines, "End If;")

		$PROCESS_USED_VARS = __getArray()
		__add($PROCESS_USED_VARS, $usedVars, True)


		__add($selfIDLINES, $logic[2])
		$TMP_IDLINES = __getArray()
		__add($TMP_IDLINES, $selfIDLINES, True)
	ElseIf $logic[1] = "SWITCH" Then
		;SWITCH [0. 5+2n],[1. SWITCH],[2. ID],[3. Variable],[4+n. Condition],[5+n. SEQ Instructions]
		$selfIDLINES = __getArray()
		__add($selfIDLINES, $logic[2])

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

			$actions = __getArray()
			$innerLogic = $logic[$i+1]
			For $j = 1 To $innerLogic[0]
				__add($actions, _logicConstructor($innerLogic[$j]), True)
				__add($usedVars, $PROCESS_USED_VARS, True)
			Next
			__addIndentationX2($lines,$actions)

			__add($selfIDLINES, $logic[2])
			__add($selfIDLINES, $TMP_IDLINES, True)
		Next
		__addIndentation($lines, @TAB&"When Others => ")
		$innerLogic = $logic[5]
			For $i = 1 To $innerLogic[0]
				__addIndentationX2($lines, _logicConstructor($innerLogic[$i]))
				__add($usedVars, $PROCESS_USED_VARS, True)
			Next
		__addIndentation($lines, "End Case;")

		__add($selfIDLINES, $logic[2])
		__add($selfIDLINES, $TMP_IDLINES, True)


		$PROCESS_USED_VARS = __getArray()
		__add($PROCESS_USED_VARS,$var[2])
		__add($PROCESS_USED_VARS, $usedVars, True)


		__add($selfIDLINES, $logic[2])
		$TMP_IDLINES = __getArray()
		__add($TMP_IDLINES, $selfIDLINES, True)
	ElseIf $logic[1] = "WFOR" Then
		;WAITFOR [0. 3],[1. WFOR],[2. ID],[3. Time]
		If $SIMULATION_MODE Then Return __getArray()

		__addIndentation($lines, "Wait for "&_decodeConstructor($logic[3], $logic[2])&";")
		$PROCESS_USED_VARS = __getArray()
		__add($PROCESS_USED_VARS, $CONSTRUCTOR_USED_VARS, True)

		$TMP_IDLINES = __getArray()
		__add($TMP_IDLINES, $logic[2])
	ElseIf $logic[1] = "WON" Then
		;WAITON [0. 4],[1. WON],[2. ID],[3. Var],[4. Edge]
		If $SIMULATION_MODE Then Return __getArray()

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

		$TMP_IDLINES = __getArray()
		__add($TMP_IDLINES, $logic[2])
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
	;MsgBox(0,"ERROR","ERROR "&$code&" "&$ERROR[$code]&" with ID "&$id)
	ConsoleWrite("ERROR:"&$code&":"&$id&@CRLF)
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
Func _idlinesNull($n)
	For $i = 1 To $n
		__add($IDLINES, -1)
	Next
EndFunc
Func _simulateVar($var)
	If $var[3] = 3 Then
		$var[3] = 2
		$var[2] = "OUT_"&$var[2]
		$var[6] = "-1"
	EndIf
	Return $var
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