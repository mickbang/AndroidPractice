package com.lq.titlebar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.*

/**
 * 标题栏控件
 * @Description:
 *
 *  左边标题模式
 *
 *
 *  中间标题模式
 *  1. 右边只能显示text或者image 且text的优先级大于image
 *
 *
 * @author: mick
 * @CreateAt: 4/14/21 2:00 PM
 *
 * @UpdateUser: mick
 * @UpdateDate: 4/14/21 2:00 PM
 * @UpdateRemark:
 */

const val LEFT_WEIGHT = 0.2f
const val RIGHT_WEIGHT = 0.2f
const val CENTER_WEIGHT = 0.6f

class TitleBar : LinearLayout {
    lateinit var container: LinearLayout
    lateinit var leftContainer: RelativeLayout
    lateinit var centerContainer: RelativeLayout
    lateinit var rightContainer: RelativeLayout

    var leftImageView: ImageButton? = null
    var centerTitleTextView: TextView? = null
    var rightImageView: ImageButton? = null

    private var titleTextSize: Float = 0f
    private var titleTextColor: Int = 0
    private var titleText: String? = ""
    private var statusBarHeight: Float = 0f
    private var contentLeftPadding: Float = 0f
    private var contentRightPadding: Float = 0f
    private var titleBarHeight: Float = 0f
    private var leftImageRes: Drawable? = null
    private var titleBarBackground: Drawable? = null

    private var rightTextSize: Float = 0f
    private var rightTextColor: Int = 0
    private var rightText: String? = ""
    private var rightImageRes: Drawable? = null

    var isLeftMode = false
    lateinit var leftModelLeftContainer: LinearLayout

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(context, attrs)
        initView(context)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val typeArray = context.obtainStyledAttributes(it, R.styleable.TitleBar)
            statusBarHeight = typeArray.getDimension(R.styleable.TitleBar_titleStatusBarHeight, 0f)

            titleBarHeight = typeArray.getDimension(R.styleable.TitleBar_titleBarHeight, dp2px(50f))
            titleBarBackground = typeArray.getDrawable(R.styleable.TitleBar_titleBarBackground)

            contentLeftPadding =
                typeArray.getDimension(R.styleable.TitleBar_contentLeftPadding, dp2px(16f))
            contentRightPadding =
                typeArray.getDimension(R.styleable.TitleBar_contentRightPadding, dp2px(16f))

            leftImageRes = typeArray.getDrawable(R.styleable.TitleBar_leftImage)
            titleTextSize = typeArray.getDimension(R.styleable.TitleBar_titleTextSize, dp2px(18f))
            titleText = typeArray.getString(R.styleable.TitleBar_titleText)
            titleTextColor = typeArray.getColor(R.styleable.TitleBar_titleTextColor, Color.BLACK)

            rightImageRes = typeArray.getDrawable(R.styleable.TitleBar_rightImage)
            rightTextSize = typeArray.getDimension(R.styleable.TitleBar_rightTextSize, dp2px(13f))
            rightText = typeArray.getString(R.styleable.TitleBar_rightText)
            rightTextColor = typeArray.getColor(R.styleable.TitleBar_rightTextColor, Color.BLACK)
        }
    }

    private fun initView(context: Context) {
        orientation = VERTICAL
        if (titleBarBackground != null) {
            background = titleBarBackground
        }
        if (statusBarHeight > 0) {
            setPadding(0, statusBarHeight.toInt(), 0, 0)
        }

        container = LinearLayout(context)
        container.orientation = HORIZONTAL
        container.setBackgroundColor(Color.BLUE)
        container.setPadding(contentLeftPadding.toInt(), 0, contentRightPadding.toInt(), 0)
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, titleBarHeight.toInt())
        addView(container, layoutParams)
        if (!isLeftMode) {
            leftContainer = RelativeLayout(context)
            val leftLayoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT)
            leftLayoutParams.weight = LEFT_WEIGHT
            centerContainer = RelativeLayout(context)
            val centerLayoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT)
            centerLayoutParams.weight = CENTER_WEIGHT
            rightContainer = RelativeLayout(context)
            val rightLayoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT)
            rightLayoutParams.weight = RIGHT_WEIGHT

            container.addView(leftContainer, leftLayoutParams)
            container.addView(centerContainer, centerLayoutParams)
            container.addView(rightContainer, rightLayoutParams)

            initCenterTitle(context)
        } else {

        }
    }

    private fun initCenterTitle(context: Context) {
        createCenterLeft(context)
        createCenterCenter(context)
        createCenterRight(context)
    }

    private fun createCenterLeft(context: Context) {
        if (leftImageRes != null) {
            leftImageView = ImageButton(context)
            leftImageView?.setImageDrawable(leftImageRes)
            leftImageView?.setBackgroundResource(android.R.color.transparent)
            val layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
            leftContainer.addView(leftImageView, layoutParams)
        }
    }

    private fun createCenterCenter(context: Context) {
        centerTitleTextView = TextView(context)
        centerTitleTextView?.setTextColor(titleTextColor)
        centerTitleTextView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize)
        centerTitleTextView?.gravity = Gravity.CENTER
        centerTitleTextView?.setSingleLine()
        centerTitleTextView?.ellipsize = TextUtils.TruncateAt.valueOf("END")
        centerTitleTextView?.text = titleText
        centerTitleTextView?.setBackgroundColor(Color.GREEN)
        val layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        centerContainer.addView(centerTitleTextView, layoutParams)
    }

    private fun createCenterRight(context: Context) {
        if (!TextUtils.isEmpty(rightText)) {
            centerTitleTextView = TextView(context)
            centerTitleTextView?.setTextColor(rightTextColor)
            centerTitleTextView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize)
            centerTitleTextView?.gravity = Gravity.CENTER
            centerTitleTextView?.setSingleLine()
            centerTitleTextView?.ellipsize = TextUtils.TruncateAt.valueOf("END")
            centerTitleTextView?.text = rightText
            val layoutParams =
                RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
            layoutParams.addRule(RelativeLayout.ALIGN_RIGHT)
            rightContainer.addView(centerTitleTextView, layoutParams)
        } else if (rightImageRes != null) {
            rightImageView = ImageButton(context)
            rightImageView?.id = R.id.iv_title_right
            rightImageView?.setBackgroundColor(Color.GRAY)
            rightImageView?.setImageDrawable(rightImageRes)
            rightImageView?.setBackgroundResource(android.R.color.transparent)
            val layoutParams =
                RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
            layoutParams.addRule(RelativeLayout.ALIGN_RIGHT)
            rightContainer.addView(rightImageView, layoutParams)
        } else {

        }
    }


    private fun dp2px(dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale + 0.5f
    }
}