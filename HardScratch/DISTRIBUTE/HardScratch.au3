#Region ;**** Directives created by AutoIt3Wrapper_GUI ****
#AutoIt3Wrapper_Icon=icon_light.ico
#AutoIt3Wrapper_Outfile=HardScratch.exe
#AutoIt3Wrapper_Res_Description=HardScratch Launcher
#AutoIt3Wrapper_Res_Fileversion=1.0.0.7
#AutoIt3Wrapper_Res_Fileversion_AutoIncrement=y
#AutoIt3Wrapper_Res_ProductName=Hard Scratch
#AutoIt3Wrapper_Res_ProductVersion=0.9
#AutoIt3Wrapper_Res_CompanyName=Liveployers
#AutoIt3Wrapper_Res_LegalCopyright=B0vE
#AutoIt3Wrapper_Res_LegalTradeMarks=Borja Live
#EndRegion ;**** Directives created by AutoIt3Wrapper_GUI ****
;
#include <File.au3>
#include <Array.au3>
#include <FTPEx.au3>
#include <WindowsConstants.au3>
#include <GUIConstantsEx.au3>
#include <StaticConstants.au3>
#include "..\..\VME2\_RunWaitGet.au3"

Const $DEBUG = False

If Not FileExists(@AppDataDir&"\HardScratch") Then DirCreate(@AppDataDir&"\HardScratch")
$file = @AppDataDir&"\HardScratch\javaPath.B0vE"
$java = ""

If FileExists($file) Then
	$java = FileRead($file)
	If Not FileExists($java) Then $java = ""
EndIf
If $java = "" Then
	$java = _JavaVersion()
	If $java = "" Then
		Do
			$userPath = _NotifyJavaDownload()
			$goodPath = False
			If $userPath <> "-1" Then
				$things = _FileListToArray($userPath,"*")
				If IsArray($things) Then
					For $thing in $things
						If $thing = "bin" Then
							$innerFiles = _FileListToArray($userPath&"/bin","java.exe")
							If IsArray($innerFiles) Then
								$goodPath = True
								ExitLoop
							EndIf
						EndIf
						If $thing = "java.exe" And StringMid($userPath,StringLen($userPath)-2,-1) = "bin" Then
							$userPath = StringTrimRight($userPath,4)
							$goodPath = True
							ExitLoop
						EndIf
					Next
				Else
					$goodPath = False
				EndIf
			EndIf
		Until $goodPath Or $userPath = "-1"
		If $userPath = "-1" Then
			Exit
		Else
			$java = $userPath&"\bin"
			If FileExists($file) Then FileDelete($file)
			FileWrite($file, $java)
		EndIf
	Else
		If FileExists($file) Then FileDelete($file)
		FileWrite($file, $java)
	EndIf
EndIf

If $DEBUG Then
	$logFile = "log"&__currentTime()&".txt"
	$logOutput = __runWaitGet($java&"\java -Dorg.lwjgl.util.Debug=true -jar HardScratch.jar", False)
	FileWrite($logFile, $logOutput)
	$comments = _askForDescription()
	If $comments <> "-1" Then
		FileWrite($logFile, @CRLF&@CRLF&$comments)
		_uploadLogs()
	EndIf
Else
	$logOutput = __runWaitGet($java&"\java -jar HardScratch.jar", False)
EndIf
If StringInStr($logOutput, "A JNI error has occurred", 2) <> 0 Then
	;Error de version
	FileDelete($file)
	;GUI TEMPORAL PARA SUBIER REPORTES
	$GUI = GUICreate("HardScratch Crash",420,40, -1, -1, BitOr($WS_BORDER,$WS_POPUP), BitOr($WS_EX_TOOLWINDOW,$WS_EX_TOPMOST))
	GUICtrlSetFont(GUICtrlCreateLabel("Couldn't find Java 11",10,8, 400, 25, $SS_CENTER), 15)

	GUISetState(@SW_SHOW, $GUI)
	Sleep(2000)
	GUIDelete($GUI)
	Run("HardScratch.exe")
EndIf



Func _JavaVersion()
    If FileExists("OpenJDK11") Then Return @ScriptDir&"\OpenJDK11\bin"

	$tmp = __checkFolder(@HomeDrive & "\" & "Program Files")
	If $tmp <> "" Then Return $tmp&"\bin"
	$tmp = __checkFolder(@HomeDrive & "\" & "Program Files (x86)")
	If $tmp <> "" Then Return $tmp&"\bin"
	$tmp = __checkFolder(@ScriptDir)
	If $tmp <> "" Then Return $tmp&"\bin"

	Return ""
EndFunc
Func __checkFolder($folder)
	$folders = _FileListToArray($folder, "*", 2, True)
	If Not IsArray($folders) Then Return ""

	For $folder in $folders
		$name = __getFolderName($folder)
		If StringInStr($name, "java", 2) Then Return __checkFolder($folder)
		If StringInStr($name, "jdk-1", 2) <> 0 Or StringInStr($name, "jre-1", 2) <> 0 Then Return $folder
	Next
	Return ""
EndFunc
Func __getFolderName($folder)
	If StringInStr($folder, "/") <> 0 Then
		Return StringMid($folder,StringInStr($folder,"/",2,-1),-1)
	ElseIf StringInStr($folder, "\") <> 0 Then
		Return StringMid($folder,StringInStr($folder,"\",2,-1),-1)
	EndIf
	Return ""
EndFunc
Func __currentTime()
	Return @YEAR&"-"&@MON&"-"&@MDAY&"-"&@HOUR&"-"&@MIN&"-"&@SEC
EndFunc

Func _uploadLogs()
	$files = _FileListToArray(@ScriptDir, "*.txt", 1, false)
	If Not IsArray($files) Then Return
	$firstTime = True
	For $file In $files
		If StringInStr($file,"log",2) = 0 Then ContinueLoop

		;GUI TEMPORAL PARA SUBIER REPORTES
		$GUI = GUICreate("HardScratch  Report Tool",420,40, -1, -1, BitOr($WS_BORDER,$WS_POPUP), BitOr($WS_EX_TOOLWINDOW,$WS_EX_TOPMOST))
		GUICtrlSetFont(GUICtrlCreateLabel("Subiendo reporte "&$file,10,8, 400, 25, $SS_CENTER), 15)

		GUISetState(@SW_SHOW, $GUI)


		If __FTPupload($file, "htdocs/reports", $firstTime) Then FileDelete($file)
		GUIDelete($GUI)
		$firstTime = False
	Next
EndFunc
Func __FTPupload($file, $path, $firstTime=False)
    Local $open = _FTP_Open('MyFTP Control')
    Local $conn = _FTP_Connect($open, "ftpupload.net", "epiz_23639799", "uhL873rMGKEqd")
    If @error Then
        MsgBox(48, "ERROR al subir log", "No se pudo conectar con el servidor de reportes."&@CRLF&"Error: " & @error)
    Else
		$appdataFolder = "APPDATA"&StringTrimLeft(StringTrimRight($file,4),3)
        _FTP_FilePut($conn, $file, $path&"/"&$file)
		If $firstTime Then
			_FTP_DirCreate($conn, $path&"/"&$appdataFolder)
			_FTP_DirPutContents($conn, @AppDataDir&"\HardScratch", $path&"/"&$appdataFolder, True)
		EndIf
		If @error Then
			MsgBox(48, "ERROR al subir log", "No se pudo subir el log."&@CRLF&"Error: " & @error)
		Else
			MsgBox(64, "Log enviado correctamente", "El reporte fue enviado exitosamente."&@CRLF&"Gracias por su colaboracion.")
		EndIf
    EndIf


    Local $iFtpc = _FTP_Close($conn)
    Local $iFtpo = _FTP_Close($open)

	If @error Then Return False
	Return True
EndFunc


Func _NotifyJavaDownload()
	$GUI = GUICreate("HardScratch launcher",500,200, -1, -1, $WS_BORDER, BitOr($WS_EX_TOOLWINDOW,$WS_EX_TOPMOST))
	GUICtrlSetFont(GUICtrlCreateLabel("HardScratch couldn't find Java SE 11."&@CRLF&"If you have JRE installed, click on select and choose the folder, or click Download to get the recommended version.", _
		10,10, 480, 100), 15)

	$cancel = GUICtrlCreateButton("Cancel", 15,120,150,40)
	GUICtrlSetFont($cancel,16,700)
	$select = GUICtrlCreateButton("Select", 175,120,150,40)
	GUICtrlSetFont($select,16,700)
	$download = GUICtrlCreateButton("Download", 335,120,150,40)
	GUICtrlSetFont($download,16,700)

	GUISetState(@SW_SHOW, $GUI)

	$text = ""
	While 1
        Switch GUIGetMsg()
            Case $GUI_EVENT_CLOSE
                ExitLoop
			Case $cancel
				$text = "-1"
				ExitLoop
			Case $select
				GUISetState(@SW_HIDE, $GUI)
				$text = FileSelectFolder("Select Java folder",@HomeDrive & "\" & "Program Files (x86)")
				GUISetState(@SW_SHOW, $GUI)
				ExitLoop
			Case $download
				ShellExecute("https://jdk.java.net/11/")
        EndSwitch
    WEnd


	GUIDelete($GUI)
	Return $text
EndFunc
Func _askForDescription()
	$GUI = GUICreate("HardScratch Report Tool",600,590, -1, -1, $WS_BORDER, BitOr($WS_EX_TOOLWINDOW,$WS_EX_TOPMOST))
	GUICtrlSetFont(GUICtrlCreateLabel("HardScratch ha gardado la traza generada durante su utilizacion. Este reporte no contiene datos personales, ni ninguna otra informacion presente en su ordenador. Si husted desea colaborar, puede enviar este reporte de forma automatizada pulsando el boton Enviar.", _
		10,10, 580, 85), 12)
	GUICtrlSetFont(GUICtrlCreateLabel("En caso de haberse producido errores durante la ejecucion o tiene alguna sujerencia, por favor, redactela en el cuadrante inferior. Si el cierre del programa ha sido inesperado, le agradeceriamos que describiese la situacion con la mayor cantidad de detalles."&@CRLF&@CRLF&"Le recordamos que el reporte es totalmente anonimo; no obstante, si quisiera aparecer en la lista de personas que ayudaron al desarrollo de HardScratch, puede incluir su nombre.", _
	10,90, 580, 100), 10)

	$edit = GUICtrlCreateEdit("",10,200,580,300)
	GUICtrlSetFont($edit,11)
	$cancel = GUICtrlCreateButton("Cancelar", 110,510,150,40)
	GUICtrlSetFont($cancel,16,700)
	$send = GUICtrlCreateButton("Enviar", 330,510,150,40)
	GUICtrlSetFont($send,16,700)

	GUISetState(@SW_SHOW, $GUI)

	$text = ""
	While 1
        Switch GUIGetMsg()
            Case $GUI_EVENT_CLOSE
                ExitLoop
			Case $cancel
				$text = "-1"
				ExitLoop
			Case $send
				$text = GUICtrlRead($edit)
				ExitLoop
        EndSwitch
    WEnd


	GUIDelete($GUI)
	Return $text
EndFunc