# FoodRecipes
Udemy 課程 FoodRecipes，食譜app可以使用關鍵字或依照想要搜尋的類型來顯示要查看的食譜。
以MVVM架構開發，利用dagger注入retrofit與Room。如果有開啟網路就會用api下載當前最新食譜並儲存到資料庫中，
沒有網路就會從資料庫中顯示上次儲存的資料。
實際介紹可以參考下面Ｍedium
https://naruto60913.medium.com/%E9%A3%9F%E8%AD%9Capp%E4%BB%8B%E7%B4%B9-4488f770d627

6月新增功能
＊使用FCM來推播
＊使用Firebase Analytics來記錄使用者旅程

2024/1
* 目錄改為clean architecture 架構
* 邏輯寫在usecase當中
