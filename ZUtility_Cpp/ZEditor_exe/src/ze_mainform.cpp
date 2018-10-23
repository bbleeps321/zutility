//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "ze_mainform.h"

//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TZEditorForm *ZEditorForm;
using namespace ze;
//---------------------------------------------------------------------------
__fastcall TZEditorForm::TZEditorForm(TComponent* Owner)
	: TForm(Owner)
{
}
//---------------------------------------------------------------------------
/// Initializes one text field when the form is first created.
void __fastcall TZEditorForm::FormCreate(TObject *Sender)
{
	AddNewTextField("");
}
//---------------------------------------------------------------------------
/// Creates a new empty tab.
void __fastcall TZEditorForm::NewTab1Click(TObject *Sender)
{
	AddNewTextField("");
}
//---------------------------------------------------------------------------
/// Closes the currently active tab.
void __fastcall TZEditorForm::CloseThisTab1Click(TObject *Sender)
{
	CloseTextField(m_currentField);
}
//---------------------------------------------------------------------------
/// Closes all tabs except the current tab.
void __fastcall TZEditorForm::CloseAllOtherTabs1Click(TObject *Sender)
{
	for (int i = 0; i < TabControl->PageCount; i++)
		if (i != m_currentField) CloseTextField(i);
}
//---------------------------------------------------------------------------
/// Adds a new text field to the tabpanel with the specified label.
/// Uses a default label if the specified label is empty.
void __fastcall TZEditorForm::AddNewTextField(String label)
{
	// get current number of tabs
	int numberOfTabs = TabControl->PageCount;

	TTabSheet * sheet = new TTabSheet(TabControl);
	if (label == "")
		sheet->Caption = "File " + IntToStr(numberOfTabs + 1);
	else
		sheet->Caption = String(label.c_str());
	TabControl->ActivePage = sheet;							// make new sheet the active one
	sheet->PageControl = TabControl;

	// create a new text field under TabControl1
	TRichEdit * edit = new TRichEdit(this);
	edit->Parent = sheet;
	edit->BorderStyle = bsNone;
	edit->BevelOuter = Controls::bvNone;

	// add the field to the vector of files
	ze::TextFile * txtFile = new ze::TextFile(edit, "");
	m_textFiles.push_back(txtFile);

	// show only that field, and it's now the current one
	m_currentField = numberOfTabs;

	TabControl->ActivePageIndex = m_currentField;
}
//---------------------------------------------------------------------------
/// Closes the tab page at the specified index.
void __fastcall TZEditorForm::CloseTextField(int index)
{
	// TODO: Implement closing of one tab.
}
//---------------------------------------------------------------------------

