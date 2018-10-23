//---------------------------------------------------------------------------

#ifndef xpl_xstringH
#define xpl_xstringH

#include <Classes.hpp>
#include <string>
#include <vector>

//---------------------------------------------------------------------------
class XString
{
public:
	/// Splits the original string around the specified delimiter and puts the parts
	/// in the given output vector. Returns whether or not the string was split.
	static bool Split(const std::string orig, const std::string delim, std::vector<std::string>& output);
	static bool Split(const String orig, const String delim, std::vector<String>& output);

	/// Returns whether or not the given string contains the specified delimiter.
	static bool Contains(const std::string orig, const std::string delim, bool ignoreCase);
	static bool Contains(const String orig, const String delim, bool ignoreCase);

	/// Returns the target string converted to lowercase letters.
	/// Does not change the target string.
	static std::string ToLowerCase(const std::string target);

	/// Returns the target string converted to uppercase letters.
	/// Does not change the target string.
	static std::string ToUpperCase(const std::string target);

	/// Converts the target std::string to an AnsiString and returns it.
	static String ToAnsiString(const std::string target);

	/// Converts the target AnsiString to a std::string and returns it.
	static std::string ToStdString(const String target);
};

//---------------------------------------------------------------------------
#endif
