//---------------------------------------------------------------------------

#include <vcl.h>
#include <windows.h>
#pragma hdrstop

#include <utility>
#include "ze_mainform.h"

/// TODO: Show ZEditor in a non-modal form.
//---------------------------------------------------------------------------

const char * const VERSION_NUMBER = "1.0.0";
//TZEditorForm * MainForm;
//#pragma argsused
int WINAPI DllEntryPoint(HINSTANCE hinst, unsigned long reason, void* lpReserved)
{
	// nothing to do here...
	if (reason == DLL_PROCESS_ATTACH) {
		ZEditorForm = NULL;
		return 1; // Indicate that the dll was linked successfully.
	}
	if (reason == DLL_PROCESS_DETACH) {
		delete ZEditorForm;
    	ZEditorForm = NULL;
		return 1; // Indicate that the dll was detached successfully.
	}
	return 1;
}
//---------------------------------------------------------------------------

extern "C" __declspec(dllexport) void open_window()
{
	// create form if not created
	if (ZEditorForm == NULL) {
		ZEditorForm = new TZEditorForm(NULL);
		if (!ZEditorForm) return;
	}

    // show form if not yet shown
	if (ZEditorForm->Visible) {
		ZEditorForm->BringToFront();
		ZEditorForm->WindowState = wsNormal;
	}
	else {
		ZEditorForm->Show();
	}

	ZEditorForm->Repaint();
}

//---------------------------------------------------------------------------

// export get_version() function in Dll
extern "C" __declspec(dllexport) const char * get_version()
{
	return VERSION_NUMBER;
}
