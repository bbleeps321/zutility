#ifndef ZEditorDll_h
#define ZEditorDll_h

#include <string>
#include <vector>
#include <exception>

// Function prototypes
typedef const char  * Fget_version();
typedef const char  * Fopen_window();

class ZEditorDll {
  public:

	/// Constructor
    ZEditorDll(const char * dllpath = "")
		: m_dllPath(dllpath), m_hdll(NULL), m_getVersion(NULL)
    {
		if (m_dllPath.empty())
			m_dllPath = "ZEditorDll.dll";
	}

	/// Destructor
    ~ZEditorDll() {
        if (m_hdll != NULL)	 FreeLibrary(m_hdll);
    }

	bool openPostProc(const char * initial_dir) {
        if (m_hdll == 0 && !loadDll())
				return false;
		return true;
    }

	/// Retrieves the version number.
	const char* getVersion()
	{
		// if not done yet, load Dll into memory
		if (m_hdll == NULL && !loadDll())
			return "";
        return m_getVersion();
    }

    /// opens Window "mwahahaha".
	void openWindow()
	{
		// if not done yet, load Dll into memory
		if (m_hdll == NULL && !loadDll())
			return;
		m_openWindow();
    }


  private:

	bool loadDll()
	{
		// load dll into memory
        m_hdll = LoadLibrary(m_dllPath.c_str());
		if (m_hdll == 0)
			return false;

		// retrieve function pointers to functions declared in dll
		m_getVersion = reinterpret_cast<Fget_version*>(GetProcAddress(m_hdll, "_get_version"));
		if (m_getVersion == NULL)
			return false;

		m_openWindow = reinterpret_cast<Fopen_window*>(GetProcAddress(m_hdll, "_open_window"));
		if (m_openWindow == NULL)
			return false;

		return true;
    }

	std::string 				m_dllPath;					///< Path to MatInspectDll.dll
	HINSTANCE 					m_hdll;						///< Handle to loaded Dll
	Fget_version   	   		    *m_getVersion;				///< Function pointer to get_version() function in Dll
	Fopen_window   	   		    *m_openWindow;				///< Function pointer to open_window() function in Dll
};



#endif // ZEditorDll_h


