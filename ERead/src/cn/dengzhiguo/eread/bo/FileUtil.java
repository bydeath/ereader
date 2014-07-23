package cn.dengzhiguo.eread.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.os.Environment;

@EBean
public class FileUtil implements IFile {

	@RootContext
	Context context;
	@Override
	public File writeFile(byte[] data, String fileName) throws Exception{
		File rootPath=new File(Environment.getExternalStorageDirectory(),"eread");
		if(!rootPath.exists()){
			rootPath.mkdirs();
		}
		File file=new File(rootPath,fileName);
		file.createNewFile();
		OutputStream output=new FileOutputStream(file);
		output.write(data);
		output.flush();
		output.close();
		return file;
	}

}
