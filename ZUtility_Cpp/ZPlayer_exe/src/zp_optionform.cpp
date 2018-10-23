//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "zp_optionform.h"
#include "zp_mainform.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TOptionForm *OptionForm;
using namespace std;
//---------------------------------------------------------------------------
	/// Constructor
__fastcall TOptionForm::TOptionForm(TComponent* Owner)
	: TForm(Owner)
{
}
//---------------------------------------------------------------------------
/// Called when the form is shown.
/// Updates the states of the options controls.
void __fastcall TOptionForm::FormShow(TObject *Sender)
{
	// update component states
	LoopCheck->Checked = m_loop;
	ShuffleCheck->Checked = m_shuffle;
	ReverseCheck->Checked = m_reverse;
	HeadStartCheck->Checked = m_headstart;
	HandicapCheck->Checked = m_handicap;
	HSGapIn->Text = IntToStr(m_hsgap);
	HCGapIn->Text = IntToStr(m_hcgap);
	OnTopCheck->Checked = dynamic_cast<TForm*>(Owner)->FormStyle == fsStayOnTop;

	// set autoplay type
	if (m_autoPlay == AP_NONE) AutoPlayGroup->ItemIndex = 0;
	else if (m_autoPlay == AP_FIRST) AutoPlayGroup->ItemIndex = 1;
	else if (m_autoPlay == AP_ALL) AutoPlayGroup->ItemIndex = 2;
	else MessageDlg("Unknown auto play type!", mtWarning, TMsgDlgButtons() << mbOK, 0);

}
//---------------------------------------------------------------------------
/// Displays the options form modally.
/// Updates the list favorites.
int __fastcall TOptionForm::ShowModalOptionForm(vector<String>* fav, TColor bgColor, TColor txtColor)
{
	// update favorites list
	m_favList = fav;
	FavListBox->Clear();
	for (int i = 0; i < m_favList->size(); i++) {
		FavListBox->Items->Append(m_favList->at(i));
	}

	BGColorBox->Selected = bgColor;
	TxtColorBox->Selected = txtColor;

	Color = bgColor;
	Font->Color = txtColor;

	return ShowModal();
}
//---------------------------------------------------------------------------
/// Called when the ok button is clicked.
/// Makes the changes and closes the form.
void __fastcall TOptionForm::OKButtonClick(TObject *Sender)
{
	// make changes to member variables
	m_loop = LoopCheck->Checked;

	m_shuffle = ShuffleCheck->Checked;
	m_reverse = ReverseCheck->Checked;
	m_headstart = HeadStartCheck->Checked;
	m_handicap = HandicapCheck->Checked;
	try {
		m_hsgap = HSGapIn->Text.ToInt();
	}
	catch (EConvertError *e) {
		m_hsgap = 0;
	}
	try {
		m_hcgap = HCGapIn->Text.ToInt();
	}
	catch (EConvertError *e) {
		m_hcgap = 0;
	}
	if (OnTopCheck->Checked) dynamic_cast<TForm*>(Owner)->FormStyle = fsStayOnTop;
	else dynamic_cast<TForm*>(Owner)->FormStyle = fsNormal;

	// set autoplay type
	if (AutoPlayGroup->ItemIndex == 0) m_autoPlay = AP_NONE;
	else if (AutoPlayGroup->ItemIndex == 1) m_autoPlay = AP_FIRST;
	else if (AutoPlayGroup->ItemIndex == 2) m_autoPlay = AP_ALL;
	else MessageDlg("Unknown auto play index!", mtWarning, TMsgDlgButtons() << mbOK, 0);

	// update favorites
	m_favList->clear();
	for (int i = 0; i < FavListBox->Count; i++) {
		m_favList->push_back(FavListBox->Items->operator [](i));
	}

	// update color scheme
	dynamic_cast<TZPlayerForm*>(Owner)->SetColorScheme(BGColorBox->Selected, TxtColorBox->Selected);
	SetColorScheme(BGColorBox->Selected, TxtColorBox->Selected);

	Close();	
}
//---------------------------------------------------------------------------
/// Called when the cancel button is clicked.
/// Closes the form without making the changes.
void __fastcall TOptionForm::CancelButtonClick(TObject *Sender)
{
	Close();	
}
//---------------------------------------------------------------------------
/// Removes the selected favorites from the favorites list.
void __fastcall TOptionForm::RemoveButtonClick(TObject *Sender)
{
	FavListBox->DeleteSelected();
}
//---------------------------------------------------------------------------
/// Toggles the state of the headstart or handicap gap input field.
void __fastcall TOptionForm::HeadStartCheckClick(TObject *Sender)
{
	HSGapIn->Enabled = HeadStartCheck->Checked;
	HCGapIn->Enabled = HandicapCheck->Checked;
}
//---------------------------------------------------------------------------
/// Displays the entire song name of the item under the mouse cursor.
/// Does nothing if there is no item under the mouse cursor.
void __fastcall TOptionForm::FavListBoxMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
	TPoint * pt = new TPoint(X,Y);
	int index = FavListBox->ItemAtPos(*pt, true);
	// return if not over an item.
	if (index == -1) return;

	FavListBox->Hint = FavListBox->Items->operator [](index);

	delete pt;
}
//---------------------------------------------------------------------------
/// Moves the selected items in the favorites list up one index.
void __fastcall TOptionForm::MoveUp1Click(TObject *Sender)
{
	for (int i = 0; i < FavListBox->Count; i++) {
		// skip this iteration if item is not selected.
		if (!FavListBox->Selected[i]) continue;
		// already at top, or item above is selected, too: skip this iteration
		if (i == 0 || FavListBox->Selected[i-1]) continue;

		// swap string at this index with the one above it.
		String temp = FavListBox->Items->operator [](i);
		FavListBox->Items->Delete(i);
		FavListBox->Items->Insert(i-1, temp);

		// keep this item selected
		FavListBox->Selected[i-1] = true;
	}
}
//---------------------------------------------------------------------------
/// Moves the selected items in the favorites list down one index.
void __fastcall TOptionForm::MoveDown1Click(TObject *Sender)
{
	for (int i = FavListBox->Count - 1; i >= 0; i--) {
		// skip this iteration if item is not selected.
		if (!FavListBox->Selected[i]) continue;
		// already at bottom, or item below is selected, too: skip this iteration
		if (i == FavListBox->Count - 1 || FavListBox->Selected[i+1]) continue;

		// swap string at this index with the one below it.
		String temp = FavListBox->Items->operator [](i);
		FavListBox->Items->Delete(i);
		FavListBox->Items->Insert(i+1, temp);

		// keep this item selected
		FavListBox->Selected[i+1] = true;
	}
}
//---------------------------------------------------------------------------
/// Moves the selected items in the favorites list to the top of the list.
void __fastcall TOptionForm::MoveToTop1Click(TObject *Sender)
{
	int count = 0; // count of number of switches so far.
	for (int i = 0; i < FavListBox->Count; i++) {
		// skip this iteration if item is not selected.
		if (!FavListBox->Selected[i]) continue;

		// insert to top plus the number of inserts already done
		String temp = FavListBox->Items->operator [](i);
		FavListBox->Items->Delete(i);
		FavListBox->Items->Insert(count, temp);

		// keep this item selected
		FavListBox->Selected[count] = true;

		count++;	// increment
	}
}
//---------------------------------------------------------------------------
/// Moves the selected items in the favorites list to the bottom of the list.
void __fastcall TOptionForm::MoveToBottom1Click(TObject *Sender)
{
	int count = 0; // count of number of switches so far.
	for (int i = FavListBox->Count - 1; i >= 0; i--) {
		// skip this iteration if item is not selected.
		if (!FavListBox->Selected[i]) continue;

		// swap string at this index with the one below it.
		String temp = FavListBox->Items->operator [](i);
		FavListBox->Items->Delete(i);
		FavListBox->Items->Insert(FavListBox->Count - count, temp);

		// keep this item selected
		FavListBox->Selected[FavListBox->Count - (1 + count)] = true; // size increase by one

		count++;	// increment
	}
}
//---------------------------------------------------------------------------
/// Sets the background and text colors to the specified ones.
void __fastcall TOptionForm::SetColorScheme(TColor bgColor, TColor txtColor)
{
	Color = bgColor;

	FavSheet->Font->Color = txtColor;
	PlaySheet->Font->Color = txtColor;
	WindowSheet->Font->Color = txtColor;

}
//---------------------------------------------------------------------------

