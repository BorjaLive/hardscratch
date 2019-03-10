#include "_BUMLAY.au3"

#Region ;Constantes y codigos de error

#EndRegion

#Region ;Rutina principal

;Esto hay que eliminarlo
$cmdLine = __getArrayInicialzer("C:\Users\Margaret\AppData\Roaming\HardScratch\test\circuit.b0ve")

$file = __getArgument(1)
$raw = __BUMLAY_load($file)
_ArrayDisplay($raw)

$vars = loadVars($raw)


$lines = __getArray()
__add($lines, generateHeader())
__add($lines, generateEntity($vars))




#EndRegion

#Region ;Compilacion
Func loadVars($data)
	$vars = __getArray()

	For $i = 1 To $data[0]
		$tmp = $data[$i]
		If $tmp[1] == "VAR" Then
			$var = __getArray()
			__add($var, $tmp[2])
			__add($var, $tmp[3])
			__add($var, $tmp[4])
			__add($var, $tmp[5])
			__add($var, $tmp[6])
			__add($var, $tmp[7])
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
	"-- VME2 is an adaptation of the currently unsuported VME. "&@CRLF&" HardScratch is a Scratch-like visual interface to generate VHDL code."& @CRLF & _
	"----------------------------------------------------------------------------------" & @CRLF & _
	"library IEEE;" & @CRLF & _
	"use IEEE.STD_LOGIC_1164.ALL;" & @CRLF & _
	"use IEEE.STD_ARITH.ALL;" & @CRLF & _
	"use IEEE.STD_LOGIC_ARITH.ALL;" & @CRLF & _
	"use IEEE.STD_LOGIC_UNSIGNED.ALL;" & @CRLF & @CRLF _
	)

	Return $lineas
EndFunc
Func generateEntity($vars)
	$lineas = __getArray()

	__add($lineas, "Entity HardScratch_Entity is" & @CRLF & @TAB & "Port(" & @CRLF )

	For $i = 1 To $vars[0]
		__add($lineas, @TAB&@TAB&_variableConstructor($vars[$i]) & ($i<>$vars[0]?";":""))
	Next

	__add($lineas, "End HardScratch_Entity;"&@CRLF)

	Return $lineas
EndFunc


Func _variableConstructor($var)
	; [0. posicion],[1. Nombres],[2. IO],[3. tipo],[4. argumentos],[5. inicializacion],[6. Linea inicial],[7. Modificador],[8. process]

	return "workinprogress"
	#cs
	;_ArrayDisplay($var)
	$item = $var[7] = "" ? "Signal " : ($var[7] & " ")
	$text = ($var[7] = "" ? "" : " ") & ($var[2] = "" ? $item : " ") & $var[1] & " : " & $var[2] & " "
	If StringUpper($var[3]) = "BINARY" Then
		If $var[4] Then
			$text &= "STD_LOGIC_VECTOR("
			$partes = StringSplit($var[4], ",")
			If Not IsArray($partes) Or $partes[0] <> 2 Then Return SetError($ERROR_ARRAY_BAD_ARGUMENTS, $var[6])
			$text &= $partes[1] & " "
			If $partes[1] > $partes[2] Then
				$text &= "downto "
			Else
				$text &= "to "
			EndIf
			$text &= $partes[2] & ")"
			If $var[5] Then $text &= " := (others => " & $var[5] & ")"
		Else
			$text &= "STD_LOGIC"
			If $var[5] Then $text &= " := " & $var[5]
		EndIf
	ElseIf StringUpper($var[3]) = "INTEGER" Then
		If $var[4] Then
			$text &= "INTEGER range "
			$partes = StringSplit($var[4], ",")
			If Not IsArray($partes) Or $partes[0] <> 2 Then Return SetError($ERROR_ARRAY_BAD_ARGUMENTS, $var[6])
			$text &= $partes[1] & " "
			If $partes[1] > $partes[2] Then
				$text &= "downto "
			Else
				$text &= "to "
			EndIf
			$text &= $partes[2]
			If $var[5] Then $text &= " := " & $var[5]
		Else
			Return SetError($ERROR_INTEGER_BOUNDAGES_NOT_DEFINED, $var[6])
		EndIf
	Else
		Local $empty[0]
		If __esNombreTipoVariable($var[3], $empty, $VAR_EXTRA_TYPES) Then
			$text &= $var[3]
			If $var[5] <> "" Then $text &= " := " & $var[5]
			Vlog("EXTRA VARIABLE USED!")
		Else
			Return SetError($ERROR_UNKNOWN_VAR_TYPE, $var[6])
		EndIf
	EndIf

	Return $text
	#ce
EndFunc
#EndRegion

#Region Internal UDF
Func __getArgument($n)
	If $n > $cmdLine[0] Then return ""
	Return $cmdLine[$n]
EndFunc

#EndRegion