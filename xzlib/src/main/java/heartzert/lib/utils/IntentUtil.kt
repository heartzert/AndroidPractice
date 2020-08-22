package heartzert.lib.utils

import android.content.Intent

/**
 * Created by heartzert on 2020/8/22.
 * Email: heartzert@163.com
 */

/**
 * 获取Long型数据或null
 */
fun Intent.getLongExtraOrNull(key: String) = this.getLongExtra(key, 0).let { if (it == 0L) null else it }

/**
 * 获取Int型数据或null
 */
fun Intent.getIntExtraOrNull(key: String) = this.getIntExtra(key, 0).let { if (it == 0) null else it }

/**
 * 获取String型数据或null
 */
fun Intent.getStringExtraOrNull(key: String) = this.getStringExtra(key).let { if (it == "") null else it }
