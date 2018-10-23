//---------------------------------------------------------------------
#include <vcl.h>
#pragma hdrstop

#include "AboutForm.h"
//--------------------------------------------------------------------- 
#pragma resource "*.dfm"
TAboutBox *AboutBox;
//---------------------------------------------------------------------
/// Constructor
__fastcall TAboutBox::TAboutBox(TComponent* AOwner)
	: TForm(AOwner)
{
}
//---------------------------------------------------------------------
/// Closes the form.
void __fastcall TAboutBox::OKButtonClick(TObject *Sender)
{
	Close();
}
//---------------------------------------------------------------------------
 
