# RuoYi-Vue Docker Compose

本项目是 RuoYi-Vue 3.8.8 框架的 Docker Compose 部署版本，简化了 RuoYi 项目的部署和运行过程。

## 安装说明

### 前置要求

- 安装 [Docker](https://docs.docker.com/get-docker/)
- 安装 [Docker Compose](https://docs.docker.com/compose/install/)

### 安装步骤

1. 克隆本仓库到本地：

   ```bash
   git clone https://github.com/artibix/ruoyi-vue-docker-compose.git
   cd ruoyi-vue-docker-compose
   ```

2. 根据需要修改 `compose.yml` 文件中的环境变量。修改时需要同步修改 ruoyi 的配置文件

3. 构建并启动服务：

   ```bash
   docker-compose up -d
   ```

## 运行说明

- 启动所有服务：

  ```bash
  docker-compose up -d
  ```

- 停止所有服务：

  ```bash
  docker-compose down
  ```

- 查看服务日志：

  ```bash
  docker-compose logs -f
  ```

- 访问应用：
  - 前端页面：`http://localhost:80`
  - 后端接口：`http://localhost:8080`

## 注意事项

- 首次运行时，数据库初始化可能需要一些时间，请耐心等待。
- 确保所需端口（如80、8080等）未被其他应用占用。
- 在生产环境中使用时，请务必修改默认密码和配置。

## 贡献

欢迎提交 Issue 或 Pull Request 来改进这个项目。

## 许可证

本项目遵循 MIT 许可证。详情请参阅 [LICENSE](LICENSE) 文件。