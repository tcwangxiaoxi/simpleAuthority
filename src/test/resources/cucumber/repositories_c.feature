# language: zh-CN 
@txn
功能: 通过isbn号找到一本书
	背景: 预加载书的信息 
		假设 "cucumber/packt-books"路径将作为书的原始信息被加载
	场景: 加载一本书 
		假设  确定数据中有2本书可用
		当 通过isbn号为"978-1-78398-478-7"搜索时
		那么 书名为"Orchestrating Docker"的将被找到。