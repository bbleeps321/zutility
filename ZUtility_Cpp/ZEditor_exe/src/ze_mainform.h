//---------------------------------------------------------------------------

#ifndef ze_mainformH
#define ze_mainformH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ComCtrls.hpp>
#include <ExtCtrls.hpp>
#include <Menus.hpp>
#include <vector>
#include <string>
//---------------------------------------------------------------------------
#include "ze_textfile.h"
//---------------------------------------------------------------------------
class TZEditorForm : public TForm
{
__published:	// IDE-managed Components
	TMainMenu *MainMenu1;
	TMenuItem *File1;
	TMenuItem *Edit1;
	TMenuItem *Options1;
	TMenuItem *About1;
	TMenuItem *Open1;
	TMenuItem *Save1;
	TMenuItem *SaveAs1;
	TMenuItem *Exit1;
	TMenuItem *Cut1;
	TMenuItem *Copy1;
	TMenuItem *Paste1;
	TPanel *TabPanel;
	TPageControl *TabControl;
	TPopupMenu *TextFieldPopupMenu;
	TMenuItem *NewTab1;
	TMenuItem *CloseThisTab1;
	TMenuItem *CloseAllOtherTabs1;

	/// Adds a text field when the form is first created.
	void __fastcall FormCreate(TObject *Sender);

	/// Creates a new empty tab.
	void __fastcall NewTab1Click(TObject *Sender);

    /// Closes the currently active tab.
	void __fastcall CloseThisTab1Click(TObject *Sender);

	/// Closes all tabs except the current tab.
	void __fastcall CloseAllOtherTabs1Click(TObject *Sender);

private:	// User declarations
	unsigned int 						m_currentField;			// current text field shown.
	std::vector< ze::TextFile* >			m_textFiles;			// list of text fields.

	/// Adds a new text field to the tabpanel with the specified label.
	/// Uses a default label if the specified label is empty.
	void __fastcall AddNewTextField(String label);

	/// Closes the tab page at the specified index.
	void __fastcall CloseTextField(int index);


public:		// User declarations
	/// Constructor.
	__fastcall TZEditorForm(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TZEditorForm *ZEditorForm;
//---------------------------------------------------------------------------
#endif
