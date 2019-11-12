package com.adpf.modules.gdtm.service.dpiinf;

import java.io.ByteArrayOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import org.apache.commons.codec.binary.Base64;

public class ZipWithZlib {

	// 压缩字符串
	public static String compressData(String data) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DeflaterOutputStream zos = new DeflaterOutputStream(bos);
			zos.write(data.getBytes());
			zos.close();
			String ret = new String(getenBASE64inCodec(bos.toByteArray()));
			return ret.replaceAll("[\\s*\t\n\r]", "");
		} catch (Exception ex) {
			ex.printStackTrace();
			return "ZIP_ERR";
		}
	}

	// 使用apche codec对数组进行encode
	public static String getenBASE64inCodec(byte[] b) {
		if (b == null)
			return null;
		return new String((new Base64()).encode(b));
	}

	// base64转码为string

	public static byte[] getdeBASE64inCodec(String s) {
		if (s == null)
			return null;
		return new Base64().decode(s.getBytes());
	}

	// 解码字符串
	public static String decompressData(String encdata) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			InflaterOutputStream zos = new InflaterOutputStream(bos);
			zos.write(getdeBASE64inCodec(encdata));
			// byte [] b = encdata.getBytes();
			// int len = b.length;
			// zos.write(b, 0, len);
			// zos.write(getdeBASE64(encdata.getBytes()),0,(encdata.getBytes()).length);
			zos.close();
			return new String(bos.toByteArray());
		} catch (Exception ex) {
			ex.printStackTrace();
			return "UNZIP_ERR";
		}
	}

	public static void main(String[] args) {
		ZipWithZlib zwz = new ZipWithZlib();
		String compString = ZipWithZlib.compressData(
				"010100000,010100000,0101003300,010100000,010100000,010100000,010100000,010100000,010100000,010100000,010100000,010100000,010100000,010100000,,010100000,010100000,010100000,010100000,010100000,010100000,010100000,|46011137091376|E73071190CD98CEC82D722F851449672");
		System.out.println(compString);

		// String t =
		// "eJwVyrkBwDAIA8CFUghDhCnBz1Qe3klz1QGw5xgh0qQzoK+ewBbUgEktaVN6VG2PQdcfHHDlaPxyanblMm71QtIrXecFTrsTmw=="
		// .replaceAll("[\\s*\t\n\r]", "");
		//
		// System.out.println(t);
		// String decompString = zwz.decompressData(t);
		// System.out.println(decompString);

	}
}