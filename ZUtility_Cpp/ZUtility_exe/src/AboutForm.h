//----------------------------------------------------------------------------
#ifndef AboutFormH
#define AboutFormH
//----------------------------------------------------------------------------
#include <vcl\System.hpp>
#include <vcl\Windows.hpp>
#include <vcl\SysUtils.hpp>
#include <vcl\Classes.hpp>
#include <vcl\Graphics.hpp>
#include <vcl\Forms.hpp>
#include <vcl\Controls.hpp>
#include <vcl\StdCtrls.hpp>
#include <vcl\Buttons.hpp>
#include <vcl\ExtCtrls.hpp>
#include <ComCtrls.hpp>
//----------------------------------------------------------------------------
class TAboutBox : public TForm
{
__published:
	TButton *OKButton;
	TPageControl *PageControl;
	TTabSheet *ZUtilitySheet;
	TLabel *Comments;
	TLabel *Copyright;
	TImage *ProgramIcon;
	TLabel *Version;
	TLabel *ProductName;
	TTabSheet *ZPlayerSheet;
	TLabel *Label1;
	TLabel *Label2;
	TImage *Image1;
	TLabel *Label3;
	TLabel *Label4;

	/// Closes the form.
	void __fastcall OKButtonClick(TObject *Sender);
private:
public:
	/// Constructor
	virtual __fastcall TAboutBox(TComponent* AOwner);
};
//----------------------------------------------------------------------------
extern PACKAGE TAboutBox *AboutBox;
//----------------------------------------------------------------------------
#endif    
