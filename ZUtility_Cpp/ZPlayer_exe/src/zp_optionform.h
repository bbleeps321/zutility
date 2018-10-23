//---------------------------------------------------------------------------

#ifndef zp_optionformH
#define zp_optionformH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <Menus.hpp>
#include <ExtCtrls.hpp>
#include <ActnColorMaps.hpp>
#include <ActnMan.hpp>
#include <ComCtrls.hpp>
#include <vector>
//---------------------------------------------------------------------------
class TOptionForm : public TForm
{
__published:	// IDE-managed Components
	TButton *CancelButton;
	TButton *OKButton;
	TPageControl *OptionsPageControl;
	TTabSheet *PlaySheet;
	TCheckBox *LoopCheck;
	TCheckBox *ShuffleCheck;
	TCheckBox *ReverseCheck;
	TRadioGroup *AutoPlayGroup;
	TCheckBox *HeadStartCheck;
	TEdit *HSGapIn;
	TCheckBox *HandicapCheck;
	TEdit *HCGapIn;
	TTabSheet *WindowSheet;
	TCheckBox *OnTopCheck;
	TLabel *BGColorLabel;
	TColorBox *BGColorBox;
	TColorBox *TxtColorBox;
	TLabel *TxtColorLabel;
	TTabSheet *FavSheet;
	TListBox *FavListBox;
	TPopupMenu *FavListPopup;
	TMenuItem *MoveUp1;
	TMenuItem *MoveDown1;
	TMenuItem *MoveToTop1;
	TMenuItem *MoveToBottom1;
	TMenuItem *RemoveSelected1;
	TButton *MoveUpButton;
	TButton *MoveDownButton;
	TButton *DeleteButton;

	/// Called when the ok button is clicked.
	/// Makes the changes and closes the form.
	void __fastcall OKButtonClick(TObject *Sender);

	/// Called when the cancel button is clicked.
	/// Closes the form without making the changes.
	void __fastcall CancelButtonClick(TObject *Sender);

	/// Called when the form is shown.
	/// Updates the states of the options controls.
	void __fastcall FormShow(TObject *Sender);

	/// Removes the selected favorites from the favorites list.
	void __fastcall RemoveButtonClick(TObject *Sender);

	/// Toggles the state of the headstart gap input field.
	void __fastcall HeadStartCheckClick(TObject *Sender);

	/// Displays the entire song name of the item under the mouse cursor.
	/// Does nothing if there is no item under the mouse cursor.
	void __fastcall FavListBoxMouseMove(TObject *Sender, TShiftState Shift, int X,
		  int Y);

	/// Moves the selected items in the favorites list up one index.
	void __fastcall MoveUp1Click(TObject *Sender);

	/// Moves the selected items in the favorites list down one index.
	void __fastcall MoveDown1Click(TObject *Sender);

	/// Moves the selected items in the favorites list to the top of the list.
	void __fastcall MoveToTop1Click(TObject *Sender);

	/// Moves the selected items in the favorites list to the bottom of the list.
	void __fastcall MoveToBottom1Click(TObject *Sender);

	/// Sets the background and text colors to the specified ones.
	void __fastcall SetColorScheme(TColor bgColor, TColor txtColor);

public:		// User declarations
	enum AUTOPLAY_TYPE {
		AP_NONE,
		AP_FIRST,
		AP_ALL,
		AP_UNKNOWN
	};

	bool						m_loop;					///< if looping is enabled.
	AUTOPLAY_TYPE 				m_autoPlay;				///< the mode of auto play that has been selected.
	bool						m_shuffle;				///< if shuffling is enabled.
	bool						m_reverse;				///< if playing backwards is enabled.
	bool						m_headstart;			///< if the head start option is enabled.
	bool						m_handicap;				///< if the handicap option is enabled.
	int							m_hsgap;				///< the time gap of the head start, in seconds.
	int							m_hcgap;				///< the time gap of the handicap, in seconds.
	std::vector<String>		   *m_favList;				///< list of favorites.

	/// Constructor
	__fastcall TOptionForm(TComponent* Owner);

	/// Displays the options form modally.
	/// Updates the list favorites.
	int __fastcall ShowModalOptionForm(std::vector<String>* fav, TColor bgColor, TColor txtColor);

private:	// User declarations

};

//---------------------------------------------------------------------------
extern PACKAGE TOptionForm *OptionForm;
//---------------------------------------------------------------------------
#endif
