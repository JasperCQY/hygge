package com.szyooge.util;

/**
 * 类似StringBuilder
 * @author HugSunshine
 *
 */
public class ByteBuilder implements java.io.Serializable{
	private static final long serialVersionUID = -4755266480259300021L;

	private int len=0;
	private byte[] data;
	private int delta=10;
	
	public ByteBuilder(){
		data = new byte[10];
	}
	
	public ByteBuilder(byte[] arg){
		if(arg.length > delta) {
			data = new byte[(arg.length/10+1)*10];
		} else {
			data = new byte[10];
		}
		// copy data
		System.arraycopy(arg, 0, data, 0, arg.length);
		// set length
		len = arg.length;
	}
	
	/**
	 * 返回byte长度
	 * @return
	 */
	public int length(){
		return len;
	}
	
	/**
	 * 返回所占空间
	 * @return
	 */
	public int capacity(){
		return data.length;
	}
	
	/**
	 * 在末尾添加一个byte数组
	 * @param arg
	 * @return
	 */
	public ByteBuilder append(byte[] arg) {
		if(data.length - len > arg.length) {
			System.arraycopy(arg, 0, data, len, arg.length);
		} else {
			int bs = (arg.length + this.capacity())/this.capacity();
			int ys = (arg.length + this.capacity())%this.capacity();
			byte[] temp = data;
			if(ys > this.capacity()/2) {
				data = new byte[(bs+1)*this.capacity()];
			} else {
				data = new byte[(int)((bs+0.5)*this.capacity())];
			}
			System.arraycopy(temp, 0, data, 0, temp.length);
		}
		System.arraycopy(arg, 0, data, len, arg.length);
		len += arg.length;
		return this;
	}
	
	/**
	 * 把一个byte数组0-length长度的数据添加到末尾，如果length比arg.length大，则把arg.length全部添加到末尾
	 * @param arg
	 * @param length
	 * @return
	 */
	public ByteBuilder append(byte[] arg, int length) {
		if(length > arg.length){
			length = arg.length;
		}
		if(data.length - len > length) {
			System.arraycopy(arg, 0, data, len, length);
		} else {
			int bs = (length + this.capacity())/this.capacity();
			int ys = (length + this.capacity())%this.capacity();
			byte[] temp = data;
			if(ys > this.capacity()/2) {
				data = new byte[(bs+1)*this.capacity()];
			} else {
				data = new byte[(int)((bs+0.5)*this.capacity())];
			}
			System.arraycopy(temp, 0, data, 0, temp.length);
		}
		System.arraycopy(arg, 0, data, len, length);
		len += length;
		return this;
	}
	/**
	 * 添加一个byte
	 * @param arg
	 * @return
	 */
	public ByteBuilder append(byte arg) {
		if(data.length == len) {
			int bs = (1 + this.capacity())/this.capacity();
			byte[] temp = data;
			data = new byte[(int)((bs+0.5)*this.capacity())];
			System.arraycopy(temp, 0, data, 0, temp.length);
		}
		data[len] = arg;
		len++;
		return this;
	}
	
	/**
	 * 返回byte数组
	 * @return
	 */
	public byte[] toBytes(){
		byte[] temp = new byte[len];
		System.arraycopy(data, 0, temp, 0, len);
		return temp;
	}
}
