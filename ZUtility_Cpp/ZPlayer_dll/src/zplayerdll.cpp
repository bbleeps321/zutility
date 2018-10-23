//---------------------------------------------------------------------------

#include <vcl.h>
#include <windows.h>
#pragma hdrstop

#include <utility>
#include "zp_mainform.h"

/// TODO: Show ZPlayer in a non-modal form.
//---------------------------------------------------------------------------

const char * const VERSION_NUMBER = "1.0.0";
//TZPlayerForm * MainForm;
//#pragma argsused
int WINAPI DllEntryPoint(HINSTANCE hinst, unsigned long reason, void* lpReserved)
{
	// nothing to do here...
	if (reason == DLL_PROCESS_ATTACH) {
		ZPlayerForm = NULL;
		return 1; // Indicate that the dll was linked successfully.
	}
	if (reason == DLL_PROCESS_DETACH) {
		delete ZPlayerForm;
    	ZPlayerForm = NULL;
		return 1; // Indicate that the dll was detached successfully.
	}
	return 1;
}
//---------------------------------------------------------------------------

extern "C" __declspec(dllexport) void open_window()
{
	// create form if not created
	if (ZPlayerForm == NULL) {
		ZPlayerForm = new TZPlayerForm(NULL);
		if (!ZPlayerForm) return;
	}

    // show form if not yet shown
	if (ZPlayerForm->Visible) {
		ZPlayerForm->BringToFront();
		ZPlayerForm->WindowState = wsNormal;
	}
	else {
		ZPlayerForm->Show();
	}

	ZPlayerForm->Repaint();
}

//---------------------------------------------------------------------------

// export get_version() function in Dll
extern "C" __declspec(dllexport) const char * get_version()
{
	return VERSION_NUMBER;
}