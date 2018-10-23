//---------------------------------------------------------------------------


#pragma hdrstop

#include "xpl_xvector.h"

//---------------------------------------------------------------------------
using namespace std;
template <class T>
//---------------------------------------------------------------------------
/// Returns the index of the element inside the vector.
/// Returns -1 if the element cannot be found in the vector.
int XVector::IndexOf(const vector<T> vector, const T element)
{
	for (int i = 0; i < vector.size(); i++) {
		if (vector[i] == element) return i;
	}
	return -1;
}
//---------------------------------------------------------------------------
/// Returns whether or not the given element is in the specified vector
/// using the == operator.
template <typename T>
bool XVector::Contains(const vector<T> vector, const T element)
{
	for (int i = 0; i < vector.size(); i++) {
		if (vector[i] == element) return true;
	}
	return false;
}
//---------------------------------------------------------------------------

#pragma package(smart_init)
