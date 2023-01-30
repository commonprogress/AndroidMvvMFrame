package com.base.commonality.utils

import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.widget.ImageView
import coil.EventListener
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.decode.VideoFrameDecoder
import coil.dispose
import coil.load
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.jhonjson.lib_base.R
import com.base.commonality.BaseAppliction
import java.io.File

/**
 * @Author: jhosnjson
 * @Time: 2023/1/30
 * description：
 */

/**加载远端图片*/
fun ImageView.loadImage(url: String) {
    CoilUtils.loadImage(this, url)
}

/**加载远端图片*/
fun ImageView.loadImageDefault(url: String, resoure: Int) {
    CoilUtils.loadImageDefault(this, url, resoure)
}

/**加载远端图片*/
fun ImageView.loadImageCenterCrop(url: String) {
    CoilUtils.loadImageCenterCrop(this, url)
}

/**加载远端圆形图片*/
fun ImageView.loadCircleImage(url: String) {
    CoilUtils.loadCircleImage(this, url)
}

/**加载远端视频 帧*/
fun ImageView.loadImageVideo(url: String) {
    CoilUtils.loadImageVideo(this, url)
}

/**加载远端圆角图片*/
fun ImageView.loadRoundImage(url: String, angle: Float) {
    CoilUtils.loadRoundImage(this, url, angle)
}

/**加载本地圆角图片*/
fun ImageView.loadRoundImage(resoure: Int, angle: Float) {
    CoilUtils.loadRoundImage(this, resoure, angle)
}


/**加载本地图片资源*/
fun ImageView.loadImage(resoure: Int) {
    CoilUtils.loadImage(this, resoure)
}

/**加载本地圆角图片资源*/
fun ImageView.loadCircleImage(resoure: Int) {
    CoilUtils.loadCircleImage(this, resoure)
}

/**加载本地视频资源  文件*/
fun ImageView.loadImage(file: File) {
    CoilUtils.loadImage(this, file)
}

/**加载本地视频资源  文件*/
fun ImageView.loadLocalImage(file: File) {
    CoilUtils.loadCircleImage(this, file)
}

/**加载本地圆角图片*/
fun ImageView.loadRoundImage(file: File, angle: Float) {
    CoilUtils.loadRoundImage(this, file, angle)
}

/**加载远端gif*/
fun ImageView.loadImageGif(url: String) {
    CoilUtils.loadImageGif(this, url)
}

/**加载本地gif*/
fun ImageView.loadImageGif(resoure: Int) {
    CoilUtils.loadImageGif(this, resoure)
}

/**加载远端svg*/
fun ImageView.loadImageSvg(url: String) {
    CoilUtils.loadImageSvg(this, url)
}

/**加载远端svg*/
fun ImageView.loadImageSvg(url: String, listener: EventListener) {
    CoilUtils.loadImageSvg(this, url, listener)
}

/**加载本地svg*/
fun ImageView.loadLocalSvg(file: File, listener: EventListener) {
    CoilUtils.loadLocalSvg(this, file, listener)
}

/**加载本地svg*/
fun ImageView.clear() {
    this.dispose()
}

object CoilUtils {

    val imageLoader = ImageLoader.Builder(BaseAppliction.mContext)
        .crossfade(true)
        .placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)


    /**加载视频首帧*/
    fun loadImageDefault(imageView: ImageView, url: String, resoure: Int) {
        val imageLoader = ImageLoader.Builder(BaseAppliction.mContext)
            .placeholder(resoure)
            .build()
        imageView.load(url, imageLoader)
    }

    /**加载远端图片*/
    fun loadImageVideo(imageView: ImageView, url: String) {
        val imageLoader = ImageLoader.Builder(BaseAppliction.mContext)
            .components {
//                add(VideoFrameFileFetcher(BaseAppliction.mContext))
//                add(VideoFrameUriFetcher(BaseAppliction.mContext))
                add(VideoFrameDecoder.Factory())
            }
            .build()
        imageView.load(url, imageLoader)
    }


    /**加载远端图片*/
    fun loadImageRoundDefault(imageView: ImageView, url: String, resoure: Int, angle: Float) {
        val round = ScreenUtils.dip2px(BaseAppliction.mContext, angle).toFloat()
        imageView.load(url) {
            transformations(
                RoundedCornersTransformation(
                    topLeft = round,
                    topRight = round,
                    bottomLeft = round,
                    bottomRight = round
                )
            )
                .placeholder(resoure)
                .build()
        }

    }

    /**加载远端图片*/
    fun loadImage(imageView: ImageView, url: String) {
        imageView.load(url)
    }

    /**加载远端图片*/
    fun loadImageCenterCrop(imageView: ImageView, url: String) {
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.load(url)
    }

    /**加载远端圆形图片*/
    fun loadCircleImage(imageView: ImageView, url: String) {
        imageView.load(url) {
            transformations(RoundedCornersTransformation(360f))
        }
    }

    /**加载远端圆角图片*/
    fun loadRoundImage(imageView: ImageView, url: String, angle: Float) {
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        val round = ScreenUtils.dip2px(BaseAppliction.mContext, angle).toFloat()
        imageView.load(url) {
            transformations(
                RoundedCornersTransformation(
                    topLeft = round,
                    topRight = round,
                    bottomLeft = round,
                    bottomRight = round
                )
            )
        }
    }

    /**加载远端圆角图片*/
    fun loadRoundImage(imageView: ImageView, resoure: Int, angle: Float) {
        val round = ScreenUtils.dip2px(BaseAppliction.mContext, angle).toFloat()
        imageView.load(resoure) {
            transformations(
                RoundedCornersTransformation(
                    topLeft = round,
                    topRight = round,
                    bottomLeft = round,
                    bottomRight = round
                )
            )
        }
    }

    /**加载本地圆角图片*/
    fun loadRoundImage(imageView: ImageView, file: File, angle: Float) {
        val round = ScreenUtils.dip2px(BaseAppliction.mContext, angle).toFloat()
        imageView.load(file) {
            transformations(
                RoundedCornersTransformation(
                    topLeft = round,
                    topRight = round,
                    bottomLeft = round,
                    bottomRight = round
                )
            )
        }
    }

    /**加载本地图片资源*/
    fun loadImage(imageView: ImageView, resoure: Int) {
        imageView.load(resoure)
    }

    /**加载远端圆形图片*/
    fun loadCircleImage(imageView: ImageView, resoure: Int) {
        imageView.load(resoure) {
            transformations(RoundedCornersTransformation(360f))
        }
    }

    /**加载本地视频资源  文件*/
    fun loadImage(imageView: ImageView, file: File) {
        imageView.load(file)
    }


    /**加载本地视频资源  文件*/
    fun loadCircleImage(imageView: ImageView, file: File) {
        imageView.load(file) {
            transformations(RoundedCornersTransformation(360f))
        }
    }


    /**加载远端gif*/
    fun loadImageGif(imageView: ImageView, url: String) {
        val imageLoader = ImageLoader.Builder(BaseAppliction.mContext)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        imageView.load(url, imageLoader)
    }

    /**加载本地gif*/
    fun loadImageGif(imageView: ImageView, resoure: Int) {
        val imageLoader = ImageLoader.Builder(BaseAppliction.mContext)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        imageView.load(resoure, imageLoader)
    }

    /**加载远端svg*/
    fun loadImageSvg(imageView: ImageView, url: String) {
        val imageLoader = ImageLoader.Builder(BaseAppliction.mContext)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()

        imageView.load(url, imageLoader)
    }


    /**加载远端svg*/
    fun loadImageSvg(imageView: ImageView, url: String, listener: EventListener) {
        val imageLoader = ImageLoader.Builder(BaseAppliction.mContext)
            .components {
                add(SvgDecoder.Factory())
            }
            .eventListener(listener)
            .build()

        imageView.load(url, imageLoader)
    }

    /**加载本地svg*/
    fun loadLocalSvg(imageView: ImageView, file: File, listener: EventListener) {
        val imageLoader = ImageLoader.Builder(BaseAppliction.mContext)
            .components {
                add(SvgDecoder.Factory())
            }
            .eventListener(listener)
            .build()

        imageView.load(file, imageLoader)
    }


    /**加载远端url 转换成Drawable*/
    fun loadDrawable(url: String): Drawable? {
        var drawable: Drawable? = null
        val imageLoader = ImageLoader.Builder(BaseAppliction.mContext).build()
        val imageRequest = ImageRequest.Builder(BaseAppliction.mContext).data(url).target {
            drawable = it
        }.build()
        imageLoader.enqueue(imageRequest)
        return drawable

    }


}
