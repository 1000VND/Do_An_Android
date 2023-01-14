CREATE DATABASE [DoAnAndroid]
GO

USE [DoAnAndroid]
GO

CREATE TABLE [dbo].[Account](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[UserName] [varchar](50) NOT NULL,
	[Email] [nvarchar](50) NOT NULL,
	[Phone] [varchar](50) NOT NULL,
	[Pass] [varchar](50) NOT NULL
)
GO

CREATE TABLE [dbo].[Popular](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[NamePopular] [varchar](50) NOT NULL,
	[Description] [nvarchar](50) NOT NULL,
	[Price] [money] NOT NULL,
	[Discount] [varchar](10),
	[Type] [varchar](10) NOT NULL,
	[ImgUrl] [varchar](1000) NOT NULL
)
GO



