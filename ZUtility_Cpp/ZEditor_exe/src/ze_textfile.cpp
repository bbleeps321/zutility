//---------------------------------------------------------------------------
#pragma hdrstop

#include "ze_textfile.h"
using namespace ze;
//---------------------------------------------------------------------------
/// Constructor
__fastcall TextFile::TextFile(TRichEdit *edit, const std::string& file)
{
	m_textField = edit;
	m_fileName = file;
	m_textField->Font->Color = clBlack;

	// TODO: Read from file (if valid) and place text into text field.
}
//---------------------------------------------------------------------------
/// Destructor
__fastcall TextFile::TextFile()
{
	// TODO: Check if wants to save (if not already)
}
//---------------------------------------------------------------------------
#pragma package(smart_init)
