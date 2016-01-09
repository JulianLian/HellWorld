package domain;
/*
 *
 *@杨安印
 *
 *这里面注意存放的是一些常量
 *
 *
 */

public class BusinessConst
{
		// 代表用户现在选择端口线
		public final static int	PORTSELECT					= 1;
		// 代表用户现在选择文件线
		public final static int	FILESELECT					= 2;
		// 通信协议中对方要求我必须要发送的数据
		public final static int	WANTTORECEIVETHENMUSTSEND	= 17;
		// 接收到多少时才给对方发送对方需要的数据
		public final static int	THESIGNALDATA				= 136;
		// 接收到多少数据时表明数据传送完毕，于是可以处理数据了
		public final static int	CANRECEIVEHOWMANYDATA		= 4096;
}
