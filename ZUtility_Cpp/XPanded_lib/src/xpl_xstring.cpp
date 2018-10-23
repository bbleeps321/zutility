//---------------------------------------------------------------------------


#pragma hdrstop

#include "xpl_xstring.h"

//---------------------------------------------------------------------------
using namespace std;

/// Splits the original string around the specified delimiter and puts the parts
/// in the given output vector. Returns whether or not the string was split.
bool XString::Split(const string orig, const string delim, vector<string>& output)
{
	// TODO: implement split
	return false;
}
//---------------------------------------------------------------------------
/// Splits the original string around the specified delimiter and puts the parts
/// in the given output vector. Returns whether or not the string was split.
bool XString::Split(const String orig, const String delim, vector<String>& output)
{
	// TODO: implement split (just call other split function)

	return false;

}
//---------------------------------------------------------------------------
/// Returns whether or not the given string contains the specified delimiter.
bool XString::Contains(const string orig, const string delim, bool ignoreCase)
{
	string whole = orig;
	if (ignoreCase) {
		whole = ToLowerCase(orig);
	}
	// found if delimiter's index not equal to "no position".
	return whole.find(delim, 0) != string::npos;
}
//---------------------------------------------------------------------------
/// Returns whether or not the given string contains the specified delimiter.
bool XString::Contains(const String orig, const String delim, bool ignoreCase)
{
	return Contains(ToStdString(orig), ToStdString(delim), ignoreCase);
}
//---------------------------------------------------------------------------
/// Returns the target string converted to lowercase letters.
/// Does not change the target string.
string XString::ToLowerCase(const string target)
{
	return ToStdString(ToAnsiString(target).LowerCase());
}
//---------------------------------------------------------------------------
/// Returns the target string converted to uppercase letters.
/// Does not change the target string.
string XString::ToUpperCase(const string target)
{
	return ToStdString(ToAnsiString(target).UpperCase());
}
//---------------------------------------------------------------------------
/// Converts the target std::string to an AnsiString and returns it.
String XString::ToAnsiString(const string target)
{
	return String(target.c_str());
}
//---------------------------------------------------------------------------
/// Converts the target AnsiString to a std::string and returns it.
string XString::ToStdString(const String target)
{
	return string(target.c_str());
}
//---------------------------------------------------------------------------

#pragma package(smart_init)
