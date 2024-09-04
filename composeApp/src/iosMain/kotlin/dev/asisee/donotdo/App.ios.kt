package dev.asisee.donotdo

import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

internal fun generateImageLoader() : ImageLoader {
    return ImageLoader {
        components {
            setupDefaultComponents(imageScope)
        }
        interceptor {
            memoryCacheConfig {
                maxSizePercent(0.25)
            }
            diskCacheConfig {
                directory(getCacheDir().toPath().resolve("image_cache"))
                maxSizeBytes(512L * 1024 * 1024)
            }
        }
    }
}

private fun getCacheDir(): String {
    return NSFileManager.defaultManager.URLForDirectory(
        NSCachesDirectory,
        NSUserDomainMask,
        null,
        true,
        null,
    )!!.path.orEmpty()
}