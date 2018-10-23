package utilities;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * Convenience class for file choosers.
 * @author Carey Zhang
 */
public class ZFileChooser
{
    private String[] ext;
    private String extDesc;
    private String initDir;

    private JFileChooser fc;

    public ZFileChooser()
    {
        initialize(null, new String[0], "");
    }

    public ZFileChooser(String initDir)
    {
        initialize(initDir, new String[0], "");
    }

    public ZFileChooser(String ext, String extDesc)
    {
        initialize(null, new String[]{ext}, extDesc);
    }

    public ZFileChooser(String initDir, String ext, String extDesc)
    {
        initialize(initDir, new String[]{ext}, extDesc);
    }

    public ZFileChooser(String[] exts, String extDesc)
    {
        initialize(null, exts, extDesc);
    }

    public ZFileChooser(String initDir, String[] exts, String extDesc)
    {
        initialize(initDir, exts, extDesc);
    }

    private void initialize(String initDir, String[] exts, String extDescs)
    {
        this.initDir = initDir;
        this.ext = exts;
        this.extDesc = extDescs;

        fc = (initDir == null) ? (new JFileChooser()) : (new JFileChooser(initDir));
        fc.setFileFilter(new FileFilter()
        {
            public boolean accept(File f)
            {
                if (f.isDirectory())
                    return true;
                for (String s : ext)
                    if (f.getPath().endsWith(s))
                        return true;
                return false;
            }

            public String getDescription()
            {
                return extDesc;
            }
        });
    }

    public File execSaveFileChooser(Component parent)
    {
        int result = fc.showSaveDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION)
            return null;
        File file = fc.getSelectedFile();
        // add ext if needed
        if (!file.getPath().contains("."))
            file = new File(file.getPath() + defaultExt());

        // check overwriting pre-existing file
        if (file.exists())
        {
            result = JOptionPane.showConfirmDialog(parent,
                    "File already exists. Overwrite?", "Confirm overwrite",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result != JOptionPane.OK_OPTION)
                return execSaveFileChooser(parent);     // redo
        }

        return file;
    }

    public File execOpenFileChooser(Component parent)
    {
        int result = fc.showOpenDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION)
            return null;
        File file = fc.getSelectedFile();
        // add ext if needed
        if (!file.getPath().contains("."))
            file = new File(file.getPath() + defaultExt());

        // check if file doesn't exist
        if (!file.exists())
        {
            result = JOptionPane.showConfirmDialog(parent,
                    "File does not exist. Choose another one.", "File not found",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION)
                return execSaveFileChooser(parent);     // redo
        }

        return file;
    }

    private String defaultExt()
    {
        if (ext.length > 0)
            return ext[0];
        return "";
    }
}
