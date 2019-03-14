; #INDEX# =======================================================================================================================
; Title .........: BUMLAY
; AutoIt Version : 3.1
; Description ...: Parser for BUMLAY markup languaje standard.
; Author(s) .....: Borja_Live (B0vE)
; ===============================================================================================================================

#include-once
#include <Array.au3>
#include "_ArrayUtils.au3"

Func __BUMLAY_load($file)
	return ___divide(___readFile($file))
EndFunc

Func tmp_read($array)
	For $i = 1 To $array[0]
		_ArrayDisplay($array[$i])
		If IsArray($array[$i]) Then
			$tmp = $array[$i]
			_ArrayDisplay($tmp[1])
		EndIf
	Next
EndFunc


Func ___readFile($file)
	return StringReplace(StringReplace(FileRead($file),@CR,""),@LF,"")
EndFunc
Func ___divide($text)
	If StringInStr($text,"|") = 0 Then Return $text

	$list = __getArray()
	$tmp = ""

	$level = 0
	For $i = 1 To StringLen($text)
		$c = StringMid($text,$i,1)


		If $level = 0 And $c = "|" Then
			__add($list,___divide(___deleteFirstAndLast($tmp)))
			$tmp = ""
		Else
			$tmp &= $c
		EndIf
		If $c = "[" Then
			$level += 1
		ElseIf $c = "]" Then
			$level -= 1
		EndIf
		;ConsoleWrite("C: "&$c&"  Level: "&$level&"  TMP: "&$tmp&@CRLF)
	Next

	;_ArrayDisplay($list)
	return $list
EndFunc


Func ___deleteFirstAndLast($text)
	If StringInStr($text,"]") = 0 Then Return $text
	$text = StringTrimLeft($text,StringInStr($text,"["))
	return StringTrimRight($text,StringLen($text)-StringInStr($text,"]",0,-1)+1)
EndFunc