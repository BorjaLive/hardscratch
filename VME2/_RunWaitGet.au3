#include <Constants.au3>

; #FUNCTION# ====================================================================================================================
; Name ..........: _RunWaitGet

; Description ...: Runs the specified process, waits for it to exit, then returns the contents of its StdOut and/or StdErr streams.
;                  Handy for running command-line tools and getting their output.
; Syntax ........: _RunWaitGet($sProgram, $nOptions, $sWorkingDir, $nShowFlag)
; Parameters ....: $sProgram            - The full path of the program (EXE, BAT, COM, or PIF) to run
;                  $nOptions            - Add options together:
;                                            1 = Capture the StdOut stream.
;                                            2 = Capture the StdErr stream.
;                                            4 = Return when the stream(s) close(s), not when the process ends.
;                  $sWorkingDir         - The working directory. Blank ("") uses the current working directory.
;                                         This is not the path to the program.
;                  $nShowFlag           - The "show" flag of the executed program:
;                                            @SW_SHOW = Show window (default)
;                                            @SW_HIDE = Hidden window (or Default keyword)
;                                            @SW_MINIMIZE = Minimized window
;                                            @SW_MAXIMIZE = Maximized window
; Return values .: String value containing the captured contents.
;                  If there was a problem running the process, @error is set to the @error value returned by Run().
;                  Otherwise, @error is 0.
; Author ........: ToasterKing

; Modified ......:
; Remarks .......:
; Related .......:
; Link ..........:https://www.autoitscript.com/forum/topic/170403-_runwaitget-stdout-and-stderr-stream-capture/
; Example .......: MsgBox(0,"System Info",_RunWaitGet(@SystemDir & "\systeminfo.exe",1))
;                  MsgBox(0,"Windows Version",_RunWaitGet(@ComSpec & " /c ver",1,"",@SW_HIDE))
; ===============================================================================================================================
Func _RunWaitGet($sProgram,$nOptions = 0,$sWorkingDir = @SystemDir,$nShowFlag = @SW_SHOW)
    Local $nRunOptFlags = 0,$sStreamOut = "" ; Initialize variables
    ; Determine flags for parent/child interaction
    If BitAND($nOptions,1) Then $nRunOptFlags += $STDOUT_CHILD
    If BitAND($nOptions,2) Then $nRunOptFlags += $STDERR_CHILD
    Local $hRunStream = Run($sProgram,$sWorkingDir,$nShowFlag,$nRunOptFlags) ; Run the process
    If @error Then Return SetError(@error,@extended,0) ; If there was an error code, return it.  Otherwise...
    While 1 ; Loop until the end of the stream, which indicates that the process has closed it (which usually means the process ended)
        If BitAND($nOptions,1) Then ; If user specified to capture STDOUT stream...
            $sStreamOut &= StdoutRead($hRunStream) ; Append new stream contents to existing variable while removing those contents from the stream.
            If @error = 2 And BitAND($nOptions,4) Then ExitLoop ; If stream ended and user specified to return when the stream closes, stop looping.
        EndIf
        If BitAND($nOptions,2) Then ; If user specified to capture STDERR stream...
            $sStreamOut &= StderrRead($hRunStream) ; Append new stream contents to existing variable while removing those contents from the stream.
            If @error = 2 And BitAND($nOptions,4) Then ExitLoop ; If stream ended and user specified to return when the stream closes, stop looping.
        EndIf
        If Not BitAND($nOptions,4) And Not ProcessExists($hRunStream) Then ExitLoop ; If using the default setting and the process ended, stop looping.
        Sleep(100) ; To avoid overloading the CPU
    WEnd

    Return SetError(0,0,$sStreamOut) ; Return the captured contents and @error = 0
EndFunc