; #INDEX# =======================================================================================================================
; Title .........: ArrayUtils
; AutoIt Version : 3.1
; Description ...: Simplified dynamic array manipulation.
; Author(s) .....: Borja_Live (B0vE)
; ===============================================================================================================================



Func __getArray()
	Local $array[1]
	$array[0] = 0
	Return $array
EndFunc   ;==>__getArray
Func __getArrayInicialzer($element)
	Local $array[2]
	$array[0] = 1
	$array[1] = $element
	Return $array
EndFunc   ;==>__getArrayInicialzer


Func __add(Byref $array, $element, $XOR = False, $isList = False)
	If $XOR And __ArrayContains($array,$element,$isList) Then Return; In case the element(s) is alrready in array
	If $isList Then
		; Is a list of elements
		For $i = 1 To $element[0]
			__add($array,$element[$i],False)
		Next
	Else
		; Is just an element
		ReDim $array[UBound($array) + 1]
		$array[UBound($array) - 1] = $element
		$array[0] += UBound($array) - 1
	EndIf
EndFunc

Func __deleteElement(ByRef $array, $number)
	Local $newArray
	For $i = 1 To $array[0]
		If IsArray($number)?__ArrayContains($number,$i):$i<>$number Then __add($newArray,$array[$i])
	Next
	$array = $newArray
EndFunc   ;==>__remove

Func __purgeRepetitions(ByRef $array)
	Local $newArray
	For $i = 1 To $array[0]
		__add($newArray,$array[0],True)
	Next
	$array = $newArray
EndFunc

Func __compareArray($array1, $array2)
	If Not IsArray($array1) Or Not IsArray($array2) Then Return False
	If $array1[0] <> $array2[0] Then Return False
	For $i = 1 To $array1[0]
		If IsArray($array1[$i]) And IsArray($array2[$i]) And Not __compareArray($array1[$i], $array2[$i]) Then Return False
		If $array1[$i] <> $array2[$i] Then Return False
	Next
	Return True
EndFunc   ;==>__compareArray

Func __ArrayContains($array, $element, $isList = False)
	$coincidences = 0
	For $i = 1 To $array[0]
		If $isList Then
			For $j = 1 To $element[0]
				If $array[$i] = $element[$j] Then $coincidences+=1
			Next
		Else
			If $array[$i] = $element Then $coincidences+=1
		EndIf
	Next
	Return $coincidences
EndFunc   ;==>__ArrayContains