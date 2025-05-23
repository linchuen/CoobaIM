### 📦 專案簡介

本專案為一個基本聊天室系統，採用單體架構開發，目標是提供一個模組化、可拓展的聊天核心服務，
讓開發者能夠方便地整合至其他業務場景，如：  
客服系統  
社交交友平台  
即時通訊服務  
並同時維持專案在一定的複雜度內，降低維護與理解成本。

[English README](https://github.com/linchuen/CoobaIM/blob/main/README-en.md)

### 🛠️ 專案環境

![env](/image/coobaIM.jpg)

### 🚀 快速啟動

1. 透過下列指令啟動所有服務：  
   初次執行會將所有依賴jar下載下來會比較久，

``` bash
docker compose up -d
```
2. 接著使用**IntelliJ IDEA**啟動主專案即可。

### 🧩 專案分層架構
本專案採用清晰的分層設計，嚴格避免跨層呼叫，提升模組可維護性與可讀性：

1. 接口層 (Controller)  
   責任：對接外部，處理請求進入點
    - 負責對外提供 RESTful API。 
    - 接收、驗證並轉換請求參數。 
    - 回傳統一格式的響應資料。 
    - 整合 Swagger / OpenAPI，提供自動化 API 文件說明。

2. 物件層 (Component)  
   責任：業務邏輯封裝與流程編排
    - 作為 Service 的中介元件，封裝複雜商業邏輯。 
    - 解耦 Service 間的直接依賴。 
    - 允許多個 Service 的邏輯並行或編排執行（非線性串接）。

3. 行為層 (Service)  
   責任：實作具體業務操作
    - 聚焦單一業務單元的實作（如：用戶管理、訊息處理等）。 
    - 對應資料結構與邏輯封裝，如依資料表前綴（user_、chat_）聚焦管理。
    - 每個 Service 類別具備單元測試能力，利於持續整合。

4. 資料層 (Repository)  
   責任：資料存取與快取邏輯抽象
   - 封裝資料庫存取邏輯，隱藏底層 SQL 實作細節。 
   - 支援複雜查詢（JOIN、分頁等）以提升查詢彈性。 
   - 整合快取策略（如 Redis），減少熱資料查詢壓力。

5. SQL 層 (Mapper)  
   責任：SQL 查詢集中管理與優化
    - 將所有 SQL 統一集中定義控管。 
    - 支援動態 SQL、生成功能。 
    - 作為與資料庫互動的最底層，方便日後替換調整 SQL 語法。
   
### 🧱模組設計補充
專案有額外將用戶連線功能獨立於core模塊(STOMP)，  
core模塊的內容預期是可替換的，如使用原生WEBSOCKET、MQTT等。

### 💻️本機開發
若要在本機運行本專案，請先將前端專案複製到 web 資料夾中：  
👉 https://github.com/linchuen/CoobaIM-app.git  
接著，執行 sql 資料夾中的 SQL 腳本來初始化資料表。  

本專案使用 STOMP 進行伺服器通訊，並預設啟用訊息佇列（MQ）功能。
目前支援 Kafka 與 ActiveMQ Artemis。若您希望以單機模式運行，
可以在 application.yml 中將以下設定設為 false：
```yaml
stomp:
  artemis:
    enabled: false
  kafka:
    enabled: false
```
⚠️ 若使用 Kafka，請確保每個伺服器實例都有唯一的 group ID，以避免衝突。

