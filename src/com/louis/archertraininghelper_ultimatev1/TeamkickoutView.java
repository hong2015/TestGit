package com.louis.archertraininghelper_ultimatev1;

import java.io.InputStream;
import com.thx.sjd.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TeamkickoutView extends View {
	int TeamkickoutView_origin_x = 200;// 靶面中心x
	int TeamkickoutView_origin_y = 200;// 靶面中心y
	public Paint TeamkickoutView_paint;// 声明画笔
	public Paint TeamkickoutView_rectpaint;
	public Paint TeamkickoutView_apaint;
	public Canvas TeamkickoutView_canvas;// 画布
	public Bitmap TeamkickoutView_bitmap;// 位图
	public Bitmap TeamkickoutView_bitmap2;
	private static final int TeamkickoutView_MyScreenWidth = 400;// view宽度
	private static final int TeamkickoutView_MyScreenHeight = 400;// view高度
	public Rect TeamkickoutView_src;
	public Rect TeamkickoutView_dst;
	public int TeamkickoutView_x;
	public int TeamkickoutView_y;
	public int TeamkickoutView_totalpoints = 0;// 总点数
	// 标号
	public int teamnumber = 0;
	public int[] archersnumber = new int[2];// 运动员序号[队伍]
	public int TeamkickoutView_Roundnumber = 0;// 轮数
	public int[][] TeamkickoutView_arrow = new int[2][3];// 箭支号每轮对应运动员箭支号[队伍][运动员]
	// 分数
	public int TeamkickoutView_tempscore = 0;// 中继分数
	public int[] TeamkickoutView_finalscore = new int[2];// 每队最终得分
	public int[][] TeamkickoutView_roundscore = new int[2][5];// 回合得分数[队伍][轮数]
	public int[][] TeamkickoutView_roundscore_rings = new int[2][5];// 回合环数[队伍][轮数]
	public int[][][][] TeamkickoutView_score_p = new int[2][3][5][2];// point的环数[队伍][运动员][轮数][箭支]
	public int[][][][] TeamkickoutView_point_x = new int[2][3][5][2];// point的x[队伍][运动员][轮数][箭支]
	public int[][][][] TeamkickoutView_point_y = new int[2][3][5][2];// point的y[队伍][运动员][轮数][箭支]
	// 标志
	public Boolean if_equle = false;
	public Boolean if_start = false;
	// 中间变量
	public int loop1 = 0;
	public int loop2 = 0;
	public int loop3 = 0;
	public int loop4 = 0;
	public int temp1 = 0;
	public int temp2 = 0;

	/* 变量定义结束 */
//	void loadcnavas() {
//		try {
//			InputStream is = getResources().openRawResource(R.drawable.target_400_blank);
//			TeamkickoutView_bitmap2 = BitmapFactory.decodeStream(is);
//			TeamkickoutView_bitmap = Bitmap.createBitmap(
//					TeamkickoutView_MyScreenWidth,
//					TeamkickoutView_MyScreenHeight, Bitmap.Config.ARGB_8888);
//			TeamkickoutView_canvas = new Canvas(TeamkickoutView_bitmap);
//			TeamkickoutView_canvas.drawBitmap(TeamkickoutView_bitmap2, 0, 0,
//					TeamkickoutView_paint);
//
//		} catch (Exception e) {
//			Log.e("Teamkickoutview", "Loading is failed.");
//		}
//	}

	public TeamkickoutView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TeamkickoutView_paint = new Paint(Paint.DITHER_FLAG);// 创建一个画笔
		TeamkickoutView_paint.setStyle(Style.STROKE);// 设置非填充
		TeamkickoutView_paint.setStrokeWidth(50);// 笔宽1像素
		TeamkickoutView_paint.setColor(Color.LTGRAY);// 设置为绿笔
		TeamkickoutView_paint.setAntiAlias(true);// 锯齿不显示
		
		TeamkickoutView_rectpaint = new Paint(Paint.DITHER_FLAG);// 创建一个画笔
		TeamkickoutView_rectpaint.setStyle(Style.FILL);// 设置填充
		TeamkickoutView_rectpaint.setStrokeWidth(10);// 笔宽1像素
		TeamkickoutView_rectpaint.setColor(Color.GREEN);// 设置为绿笔
		TeamkickoutView_rectpaint.setAntiAlias(true);// 锯齿不显示
		
		TeamkickoutView_apaint = new Paint(Paint.DITHER_FLAG);// 创建一个画笔
		TeamkickoutView_apaint.setStyle(Style.FILL);// 设置填充
		TeamkickoutView_apaint.setStrokeWidth(1);// 笔宽1像素
		TeamkickoutView_apaint.setColor(Color.WHITE);// 设置为绿笔
		TeamkickoutView_apaint.setAlpha(0xa0);
		TeamkickoutView_apaint.setAntiAlias(true);// 锯齿不显示
		
		InputStream is = getResources().openRawResource(R.drawable.target_400_blank);
		TeamkickoutView_bitmap2 = BitmapFactory.decodeStream(is);
		
		TeamkickoutView_bitmap = Bitmap.createBitmap(TeamkickoutView_MyScreenWidth, TeamkickoutView_MyScreenHeight,Bitmap.Config.ARGB_8888);
		TeamkickoutView_canvas = new Canvas(TeamkickoutView_bitmap);
		TeamkickoutView_canvas.drawBitmap(TeamkickoutView_bitmap2, 0, 0,TeamkickoutView_paint);
	}

	public void clear() {
//		loadcnavas();
//		invalidate();

	}

	// 画位图
	@Override
	protected void onDraw(Canvas TeamkickoutView_canvas) {
		TeamkickoutView_canvas.drawBitmap(TeamkickoutView_bitmap, 0, 0,TeamkickoutView_paint);
	}

	// 触屏监控
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE
				|| event.getAction() == MotionEvent.ACTION_DOWN) {
			if (TeamkickoutView_totalpoints >= 0
					&& TeamkickoutView_totalpoints < 12
					&& TeamkickoutView_Roundnumber >= 0
					&& TeamkickoutView_Roundnumber < 5) {
				TeamkickoutView_x = (int) event.getX();
				TeamkickoutView_y = (int) event.getY();
				TeamkickoutView_tempscore = score(TeamkickoutView_x,
						TeamkickoutView_y);
				TeamkickoutView_src = new Rect(TeamkickoutView_x - 25,
						TeamkickoutView_y - 25, TeamkickoutView_x + 25,
						TeamkickoutView_y + 25);
				TeamkickoutView_dst = new Rect(0, 0, 100, 100);
				TeamkickoutView_canvas.drawBitmap(TeamkickoutView_bitmap2,
						TeamkickoutView_src, TeamkickoutView_dst,
						TeamkickoutView_rectpaint);
				TeamkickoutView_canvas.drawCircle(50, 50, 10,
						TeamkickoutView_rectpaint);
//				TeamKickout_Activity.tv_teamkickoutshowrings
//						.setVisibility(View.VISIBLE);// 小比分显示
//				if (TeamkickoutView_tempscore == 11) {
//					TeamKickout_Activity.tv_teamkickoutshowrings.setText("X");
//				} else {
//					TeamKickout_Activity.tv_teamkickoutshowrings
//							.setText(TeamkickoutView_tempscore + "");
//				}
				if (distance(TeamkickoutView_origin_x,
						TeamkickoutView_origin_y, TeamkickoutView_x,
						TeamkickoutView_y) > 200) {
					clear_rect();
				}
				invalidate();
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
//			TeamKickout_Activity.tv_teamkickoutshowrings
//					.setVisibility(View.INVISIBLE);// x小比分显示
			TeamkickoutView_x = (int) event.getX();
			TeamkickoutView_y = (int) event.getY();
			TeamkickoutView_tempscore = score(TeamkickoutView_x,
					TeamkickoutView_y);
			if (TeamkickoutView_totalpoints < 12
					&& TeamkickoutView_tempscore > 0
					&& TeamkickoutView_Roundnumber < 5) {
				TeamkickoutView_canvas.drawCircle(TeamkickoutView_x,
						TeamkickoutView_y, 5, TeamkickoutView_rectpaint);

				TeamkickoutView_score_p[teamnumber][archersnumber[teamnumber]][TeamkickoutView_Roundnumber][TeamkickoutView_arrow[teamnumber][archersnumber[teamnumber]]] = TeamkickoutView_tempscore;
				// 当前箭支坐标
				TeamkickoutView_point_x[teamnumber][archersnumber[teamnumber]][TeamkickoutView_Roundnumber][TeamkickoutView_arrow[teamnumber][archersnumber[teamnumber]]] = TeamkickoutView_x;
				TeamkickoutView_point_y[teamnumber][archersnumber[teamnumber]][TeamkickoutView_Roundnumber][TeamkickoutView_arrow[teamnumber][archersnumber[teamnumber]]] = TeamkickoutView_y;
				// 分是否为决胜箭显示结果
//				if (TeamkickoutView_Roundnumber < 4) {
//					if (TeamkickoutView_tempscore == 11) {
//						TeamKickout_Activity.tv_everyscore[teamnumber][archersnumber[teamnumber]][TeamkickoutView_Roundnumber][TeamkickoutView_arrow[teamnumber][archersnumber[teamnumber]]]
//								.setText("X");
//					} else {
//						TeamKickout_Activity.tv_everyscore[teamnumber][archersnumber[teamnumber]][TeamkickoutView_Roundnumber][TeamkickoutView_arrow[teamnumber][archersnumber[teamnumber]]]
//								.setText(TeamkickoutView_tempscore + "");
//					}
//				} else {
//					// 决胜箭
//					if (TeamkickoutView_tempscore == 11) {
//						TeamKickout_Activity.tv_everyscore[teamnumber][archersnumber[teamnumber]][TeamkickoutView_Roundnumber][0]
//								.setText("X");
//					} else {
//						TeamKickout_Activity.tv_everyscore[teamnumber][archersnumber[teamnumber]][TeamkickoutView_Roundnumber][0]
//								.setText(TeamkickoutView_tempscore + "");
//					}
//				}

				/* judge who is winner */
				// 决胜箭使用
				// 每轮比分统计
				if ((TeamkickoutView_score_p[1][2][TeamkickoutView_Roundnumber][1] != 0)
						&& (!if_equle))// 非决胜轮的每轮最后一个人的最后一支箭不为零作为每轮统计节点
				{
					/* 统计本轮两队环数 */
					for (loop1 = 0; loop1 < 2; loop1++)// 队伍
					{
						for (loop2 = 0; loop2 < 3; loop2++)// 运动员
						{
							for (loop3 = 0; loop3 < 2; loop3++)// 箭支
							{
								TeamkickoutView_roundscore_rings[loop1][TeamkickoutView_Roundnumber] += TeamkickoutView_score_p[loop1][loop2][TeamkickoutView_Roundnumber][loop3];
							}
						}
					}
					/* 比较环数计算本轮两队得分 */
					if (TeamkickoutView_roundscore_rings[0][TeamkickoutView_Roundnumber] > TeamkickoutView_roundscore_rings[1][TeamkickoutView_Roundnumber]) {
						TeamkickoutView_roundscore[0][TeamkickoutView_Roundnumber] += 2;
						TeamkickoutView_roundscore[1][TeamkickoutView_Roundnumber] += 0;
					} else if (TeamkickoutView_roundscore_rings[0][TeamkickoutView_Roundnumber] < TeamkickoutView_roundscore_rings[1][TeamkickoutView_Roundnumber]) {
						TeamkickoutView_roundscore[0][TeamkickoutView_Roundnumber] += 0;
						TeamkickoutView_roundscore[1][TeamkickoutView_Roundnumber] += 2;
					} else {
						TeamkickoutView_roundscore[0][TeamkickoutView_Roundnumber] += 1;
						TeamkickoutView_roundscore[1][TeamkickoutView_Roundnumber] += 1;
					}
					// 显示每轮得分

					// 计算总得分并分胜负
					temp1 = 0;// 清空缓存
					temp2 = 0;// 清空缓存
					for (loop2 = 0; loop2 < 5; loop2++)// 轮数
					{
						temp1 += TeamkickoutView_roundscore[0][loop2];// 队伍1总分数
						temp2 += TeamkickoutView_roundscore[1][loop2];// 队伍2总分数
					}
					TeamkickoutView_finalscore[0] = temp1;
					TeamkickoutView_finalscore[1] = temp2;

					if (TeamkickoutView_finalscore[0] == 6) {
						// 队伍1为胜利者
					} else if (TeamkickoutView_finalscore[1] == 6) {
						// 队伍2为胜利者
					} else if ((TeamkickoutView_finalscore[1] == TeamkickoutView_finalscore[0])
							&& (TeamkickoutView_Roundnumber == 4))
						if_equle = true;

				} else if ((if_equle)//如果平局
						&& (TeamkickoutView_score_p[1][2][TeamkickoutView_Roundnumber][0] != 0)) {
					/* 统计本轮两队的环数 */
					for (loop1 = 0; loop1 < 3; loop1++)// 切换运动员即可
					{
						TeamkickoutView_roundscore_rings[0][TeamkickoutView_Roundnumber] += TeamkickoutView_score_p[0][loop1][TeamkickoutView_Roundnumber][0];
						TeamkickoutView_roundscore_rings[1][TeamkickoutView_Roundnumber] += TeamkickoutView_score_p[1][loop1][TeamkickoutView_Roundnumber][0];
					}
					/* 比较环数计算本轮两队得分 */
					if (TeamkickoutView_roundscore_rings[0][TeamkickoutView_Roundnumber] > TeamkickoutView_roundscore_rings[1][TeamkickoutView_Roundnumber]) {
						TeamkickoutView_roundscore[0][TeamkickoutView_Roundnumber] += 2;
						TeamkickoutView_roundscore[1][TeamkickoutView_Roundnumber] += 0;
					} else if (TeamkickoutView_roundscore_rings[0][TeamkickoutView_Roundnumber] < TeamkickoutView_roundscore_rings[1][TeamkickoutView_Roundnumber]) {
						TeamkickoutView_roundscore[0][TeamkickoutView_Roundnumber] += 0;
						TeamkickoutView_roundscore[1][TeamkickoutView_Roundnumber] += 2;
					} else {
						TeamkickoutView_roundscore[0][TeamkickoutView_Roundnumber] += 1;
						TeamkickoutView_roundscore[1][TeamkickoutView_Roundnumber] += 1;
					}
					// 显示每轮得分

					// 计算总得分并分胜负
					temp1 = 0;// 清空缓存
					temp2 = 0;// 清空缓存
					for (loop2 = 0; loop2 < 5; loop2++)// 轮数
					{
						temp1 += TeamkickoutView_roundscore[0][loop2];// 队伍1总分数
						temp2 += TeamkickoutView_roundscore[1][loop2];// 队伍2总分数
					}
					TeamkickoutView_finalscore[0] = temp1;
					TeamkickoutView_finalscore[1] = temp2;

					if (TeamkickoutView_finalscore[0] > TeamkickoutView_finalscore[1]) {
						// 队伍1为胜利者
					} else if (TeamkickoutView_finalscore[0] < TeamkickoutView_finalscore[1]) {
						// 队伍2为胜利者
					} else if (TeamkickoutView_finalscore[1] == TeamkickoutView_finalscore[0]) {
						/* 比较距离 */
					}

				}

				// 调整统计目标
				TeamkickoutView_totalpoints++;
				// 切换运动员下次统计箭支号
				TeamkickoutView_arrow[teamnumber][archersnumber[teamnumber]]++;// 每轮对应运动员箭支号[队伍][运动员]
				// 设定下次本队统计运动员序号
				if (++archersnumber[teamnumber] == 3)// 运动员序号[队伍]
				{
					archersnumber[teamnumber] = 0;
				}

				/* 切换队伍 */
				if (teamnumber == 0) {
					teamnumber++;/* chang team */
				} else
					teamnumber--;

				/* chang round */
				if ((TeamkickoutView_totalpoints == 12)) {
					TeamkickoutView_Roundnumber++;// 轮数
					archersnumber[teamnumber] = 0;// 队伍号清零
					TeamkickoutView_totalpoints = 0;// 总箭支数清零
					for (int i = 0; i < 3; i++) {// 箭支号
						TeamkickoutView_arrow[0][i] = 0;
						TeamkickoutView_arrow[1][i] = 0;
					}
				} else
					;
				clear_rect();
			}

		}
		TeamkickoutView_x = (int) event.getX();
		TeamkickoutView_y = (int) event.getY();
		return true;
	}

	public float distance(int a, int b, int x1, int y1) {
		float dis = 0;

		if (x1 > a) {
			x1 = x1 - a;
		} else {
			x1 = a - x1;
		}
		if (y1 > b) {
			y1 = y1 - b;
		} else {
			y1 = b - y1;
		}
		dis = (float) Math.sqrt(x1 * x1 + y1 * y1);
		return dis;
	}

	// 计算环数
	public int score(int x2, int y2) {
		int score = 0;
		if (distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y, x2, y2) <= 25
				&& distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
						x2, y2) > 0) {
			if (distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
					x2, y2) <= 10)
				score = 11;
			else {
				score = 10;
			}
		} else if (distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
				x2, y2) <= 45
				&& distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
						x2, y2) > 25) {
			score = 9;
		} else if (distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
				x2, y2) <= 65
				&& distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
						x2, y2) > 45) {
			score = 8;
		} else if (distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
				x2, y2) <= 85
				&& distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
						x2, y2) > 65) {
			score = 7;
		} else if (distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
				x2, y2) <= 105
				&& distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
						x2, y2) > 85) {
			score = 6;
		} else if (distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
				x2, y2) <= 125
				&& distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
						x2, y2) > 105) {
			score = 5;
		} else if (distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
				x2, y2) <= 145
				&& distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
						x2, y2) > 125) {
			score = 4;
		} else if (distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
				x2, y2) <= 165
				&& distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
						x2, y2) > 145) {
			score = 3;
		} else if (distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
				x2, y2) <= 185
				&& distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
						x2, y2) > 165) {
			score = 2;
		} else if (distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
				x2, y2) <= 205
				&& distance(TeamkickoutView_origin_x, TeamkickoutView_origin_y,
						x2, y2) > 185) {
			score = 1;
		} else {
			score = 0;
		}
		return score;

	}

	/*
	 * public void show_onearcherpreviouspoints(int whicharcher) { clear(); for
	 * (int m = 0; m < 5; m++) { for (int n = 0; n < 3; n++) { if
	 * (IndividualkickoutView_score_p[SingleKickout_coach.archer_number][m][n]
	 * != 0) { IndividualkickoutView_canvas .drawCircle(
	 * IndividualkickoutView_point_x[SingleKickout_coach.archer_number][m][n],
	 * IndividualkickoutView_point_y[SingleKickout_coach.archer_number][m][n],
	 * 5, IndividualkickoutView_rectpaint[SingleKickout_coach.archer_number]);//
	 * 画点 } } } invalidate();
	 * 
	 * }
	 */
	public void clear_rect() {
		clear();

		for (int n = 0; n < 2; n++) {
			if (TeamkickoutView_score_p[teamnumber][archersnumber[teamnumber]][TeamkickoutView_Roundnumber][n] != 0) {
				TeamkickoutView_canvas
						.drawCircle(
								TeamkickoutView_point_y[teamnumber][archersnumber[teamnumber]][TeamkickoutView_Roundnumber][n],
								TeamkickoutView_point_y[teamnumber][archersnumber[teamnumber]][TeamkickoutView_Roundnumber][n],
								5, TeamkickoutView_rectpaint);// 画点
			}
		}
		invalidate();

	}

}
