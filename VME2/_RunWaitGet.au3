#include <Constants.au3>


Func __runWaitGet($execute, $show = true, $workingdir = @ScriptDir)
	$pid = Run($execute, $workingdir, $show?@SW_SHOW:@SW_HIDE, BitOR($STDERR_CHILD, $STDOUT_CHILD))

    Local $output = ""
    While 1
        $output &= StdoutRead($pid)
        If @error Then ExitLoop
    WEnd
    While 1
        $output &= StderrRead($pid)
        If @error Then ExitLoop
	WEnd


    Return $output
EndFunc