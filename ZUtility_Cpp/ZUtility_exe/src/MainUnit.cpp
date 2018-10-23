//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "MainUnit.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TZUtilityForm *ZUtilityForm;
//---------------------------------------------------------------------------
/// Constructor
__fastcall TZUtilityForm::TZUtilityForm(TComponent* Owner)
	: TForm(Owner)
{
}
//---------------------------------------------------------------------------
/// Called when this form is first created.
/// Initializes global components.
void __fastcall TZUtilityForm::FormCreate(TObject *Sender)
{
	AboutBox = new TAboutBox(this);
}
//---------------------------------------------------------------------------
/// Called when the run zplayer action command is invoked.
/// Displays the ZPlayer window.
void __fastcall TZUtilityForm::ZPlayerButtonClick(TObject *Sender)
{
	ZPlayer.openWindow();
}
//---------------------------------------------------------------------------
/// Called when the show about info action event is invoked.
/// Displays the About window.
void __fastcall TZUtilityForm::About1Click(TObject *Sender)
{
	AboutBox->Show();
}
//---------------------------------------------------------------------------
void __fastcall TZUtilityForm::ZEditorButtonClick(TObject *Sender)
{
	ZEditor.openWindow();	
}
//---------------------------------------------------------------------------

