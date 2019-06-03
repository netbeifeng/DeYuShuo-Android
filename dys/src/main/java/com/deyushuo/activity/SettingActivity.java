package com.deyushuo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.dys.deyushuo.R;
public class SettingActivity extends Activity {
    LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this);
    LoadingDailog dialog;
    private Context mContext = this;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
        loadBuilder
                .setMessage("查找更新中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
    }

    public void onClick_checkUpdate(View v) throws InterruptedException{
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /**
                 * 延时执行的代码
                 */
                dialog.hide();
                Toast.makeText(SettingActivity.this,"暂未发现新版本哦！",Toast.LENGTH_LONG).show();
            }
        },3000); // 延时1秒

    }

    public void onClick_toMail(View v) {
        Uri uri = Uri.parse("mailto:deyushuo@163.com");
        String[] email = {"deyushuo@163.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
        intent.putExtra(Intent.EXTRA_SUBJECT, "反馈/错误报告"); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文
        startActivity(Intent.createChooser(intent, "请选择邮件类应用"));

    }

    public void onClick_about(View v) {
        Intent intent = new Intent(this,SettingReaderActivity.class);
        intent.putExtra("titel","关于");
        intent.putExtra("context","当代大学生作为与智能手机和互联网一起成长起来的一代人，APP的应用几乎出现在他们日常生活中的每一个领域。\n" +
                "\n" +
                "“德语说”APP为德语专业的大学生和德语学习者提供辅助自主学习的资料，站在学习者的角度，加强学生对课本、语法的掌握程度，激发学习者对自主学习的积极性，培养课后自主学习的习惯，同时简化学生搜索收集德语学习素材的过程，便捷高效，辅助高校培养德语专业水平一流、自主学习能力强的“互联网+”时代新型人才，让学生真切体会“以书为伴”的乐趣；拓展开发德语学习方面以APP为代表的多媒体市场，以涵盖面广、贴合生活的主题，横向适应不同版本的教材，以同一主题内素材的难易程度的划分，纵向适应不同层次的学生，旨在填补德语自主学习APP方面的空白。\n" +
                "\n" +
                "该APP分为三大板块，“书上说”、“生活说”和“名人说”。其中“书上说”以课时的方式涵盖德语教材中的的语法精解、固定搭配、特殊用法等，帮助学生更加系统便捷的了解知识重点；“生活说”则以主题的方式，按照不同难度，提供给不同层次的德语学习者拓展素材，以适应不同版本的课本，比如音频、视频、文本等，其中特放入小猪佩奇德语版视频，提高德语学习者的学习兴趣；“名人说”总结了许多德语名人名言，可使学习者更加方便的获取信息，并在日常生活中快速调取信息，利用碎片化时间提高素材储备量。\n" +
                "\n" +
                "“德语说”APP作为一个大学生自主创新的APP，内容为自己总结和在日常生活中积累，或从网络和书籍资料收集而来，整理不易，如有错误和侵权，望即刻告诉我们，作为大学生，我们随时做好改正错误的准备。\n");
        startActivity(intent);
    }

    public void onClick_from(View v) {
        Intent intent = new Intent(this,SettingReaderActivity.class);
        intent.putExtra("titel","数据来源");
        intent.putExtra("context","书上说\n" +
                "\t•Studienweg 1\n" +
                "\t•Studienweg 2\n" +
                "\t•Studienweg 3\n" +
                "\n" +
                "生活说\n" +
                "\t• Erkundungen Deutsch als Fremdsprache B1\n" +
                "\t• Erkundungen Deutsch als Fremdsprache B2\n" +
                "\t•http://www.nachrichtenleicht.de/\n" +
                "\t• Peppa Wutz\n" +
                "\n" +
                "名人说\n" +
                "\t•http://zitate.net\n");
        startActivity(intent);
    }



    public void back (View v) {
        finish();
    }

    public void onClick_share(View v){
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, "www.baidu.com");
        startActivity(Intent.createChooser(textIntent, "Test Link"));
    }





}
