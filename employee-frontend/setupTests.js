import { config } from '@vue/test-utils'

// 配置Vue Test Utils
config.global.stubs = {
  // 添加需要的存根组件
  RouterLink: true,
  RouterView: true
}
