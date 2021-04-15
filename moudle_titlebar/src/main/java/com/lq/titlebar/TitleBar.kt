package com.lq.titlebar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*

/**
 * 标题栏控件
 * @Description:
 *
 *  左边标题模式
 *  只有leftContainer与rightContainer不存在center开头的控件
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
    lateinit var container: ViewGroup
    lateinit var leftContainer: RelativeLayout
    lateinit var centerContainer: RelativeLayout
    lateinit var rightContainer: RelativeLayout

    var leftImageView: ImageButton? = null
    var titleTextView: TextView? = null
    var rightTextView: TextView? = null
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
    private var spaceIv2tv: Float = 0f
    private var rightTextSize: Float = 0f
    private var rightTextColor: Int = 0
    private var rightText: String? = ""
    private var rightImageRes: Drawable? = null

    var isLeftMode = false

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
            isLeftMode = typeArray.getBoolean(R.styleable.TitleBar_isLeftMode, false)
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
        spaceIv2tv = dp2px(10f)
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
        (container as LinearLayout).orientation = HORIZONTAL
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

            leftContainer = RelativeLayout(context)
            val leftLayoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 0.3f)

            rightContainer = RelativeLayout(context)
            val rightLayoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 0.7f)

            container.addView(leftContainer, leftLayoutParams)
            container.addView(rightContainer, rightLayoutParams)


            initLeftTitle(context)
        }
    }

    private fun initLeftTitle(context: Context) {
        createLeftLeft(context)
        createLeftRight(context)
    }

    private fun createLeftRight(context: Context) {
        if (!TextUtils.isEmpty(rightText) && rightImageRes != null) {
            buildSingleRightImage(context)
            buildRightText(context)
            val layoutParams =
                RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
            layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.iv_title_right)
            rightContainer.addView(rightTextView, layoutParams)
        } else if (!TextUtils.isEmpty(rightText)) {
            buildSingleRightText(context)
        } else if (rightImageRes != null) {
            buildSingleRightImage(context)
        } else {

        }
    }

    private fun buildRightText(context: Context) {
        rightTextView = TextView(context)
        rightTextView?.setTextColor(rightTextColor)
        rightTextView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize)
        rightTextView?.gravity = Gravity.CENTER
        rightTextView?.setSingleLine()
        rightTextView?.ellipsize = TextUtils.TruncateAt.valueOf("END")
        rightTextView?.text = rightText ?: ""
    }

    private fun createLeftLeft(context: Context) {
        leftImageView = ImageButton(context)
        leftImageView?.id = R.id.iv_title_left
        leftImageView?.setBackgroundResource(android.R.color.transparent)
        val ivLayoutParams =
            RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        ivLayoutParams.rightMargin = spaceIv2tv.toInt()
        ivLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        leftContainer.addView(
            leftImageView,
            ivLayoutParams
        )
        leftImageView?.setImageDrawable(leftImageRes)
        if (leftImageRes == null) {
            leftImageView?.visibility = GONE
        }

        buildTitleText(context)
        val layoutParams =
            RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.iv_title_left)
        leftContainer.addView(
            titleTextView,
            layoutParams
        )
    }

    private fun buildTitleText(context: Context) {
        titleTextView = TextView(context)
        titleTextView?.setTextColor(titleTextColor)
        titleTextView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize)
        titleTextView?.gravity = Gravity.CENTER
        titleTextView?.setSingleLine()
        titleTextView?.ellipsize = TextUtils.TruncateAt.valueOf("END")
        titleTextView?.text = titleText ?: ""
        titleTextView?.setBackgroundColor(Color.GREEN)
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
        titleTextView = TextView(context)
        titleTextView?.setTextColor(titleTextColor)
        titleTextView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize)
        titleTextView?.gravity = Gravity.CENTER
        titleTextView?.setSingleLine()
        titleTextView?.ellipsize = TextUtils.TruncateAt.valueOf("END")
        titleTextView?.text = titleText ?: ""
        titleTextView?.setBackgroundColor(Color.GREEN)
        val layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        centerContainer.addView(titleTextView, layoutParams)
    }

    private fun createCenterRight(context: Context) {
        if (!TextUtils.isEmpty(rightText) && rightImageRes != null) {
            buildSingleRightImage(context)
            buildRightText(context)
            val layoutParams =
                RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
            layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.iv_title_right)
            rightContainer.addView(rightTextView, layoutParams)
        } else if (!TextUtils.isEmpty(rightText)) {
            buildSingleRightText(context)
        } else if (rightImageRes != null) {
            buildSingleRightImage(context)
        } else {

        }
    }

    private fun buildSingleRightImage(context: Context) {
        buildRightImage(context)
        val layoutParams =
            RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        rightContainer.addView(rightImageView, layoutParams)
    }

    private fun buildSingleRightText(context: Context) {
        buildRightText(context)
        val layoutParams =
            RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        rightContainer.addView(rightTextView, layoutParams)
    }

    private fun buildRightImage(context: Context) {
        rightImageView = ImageButton(context)
        rightImageView?.id = R.id.iv_title_right
        rightImageView?.setBackgroundColor(Color.GRAY)
        rightImageView?.setImageDrawable(rightImageRes)
        rightImageView?.setBackgroundResource(android.R.color.transparent)
    }


    private fun dp2px(dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale + 0.5f
    }
}