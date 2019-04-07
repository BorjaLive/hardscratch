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


Func __add(Byref $array, $element, $isList = False, $XOR = False)
	If $XOR And __ArrayContains($array,$element,$isList) Then Return; In case the element(s) is alrready in array

	If $isList Then
		; Is a list of elements
		If Not IsArray($element) Then Return
		For $i = 1 To $element[0]
			__add($array,$element[$i])
		Next
	Else
		; Is just an element
		$array[0] += 1
		ReDim $array[$array[0]+1]
		$array[$array[0]] = $element
	EndIf
EndFunc

Func __deleteElement(ByRef $array, $number)
	If IsArray($number) Then
		For $i = 1 To $number[0]
			__deleteElement($array, $number)
		Next
	Else
		Local $newArray = __getArray()
		If $number = -1 Then $number = $array[0]-$number+1
		For $i = 1 To $array[0]
			If IsArray($number)?__ArrayContains($number,$i):$i<>$number Then __add($newArray,$array[$i])
		Next
		$array = $newArray
	EndIf
EndFunc   ;==>__remove

Func __purgeRepetitions(ByRef $array)
	Local $newArray = __getArray()
	For $i = 1 To $array[0]
		__add($newArray,$array[$i],False, True); Is not a list, but is in XOR mode
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