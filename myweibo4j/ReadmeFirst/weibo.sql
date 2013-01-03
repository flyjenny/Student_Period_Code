CREATE  TABLE `weibo`.`Weibo1_RepostFrom` (

  `SID` BIGINT NOT NULL COMMENT 'status id' ,

  `CreateAt` DATETIME NULL COMMENT 'status创建时间' ,

  `Text` VARCHAR(280) NULL COMMENT '微博内容' ,

  `InReplyToStatusId` BIGINT NULL COMMENT '回复ID' ,

  `InReplyToUserId` BIGINT NULL COMMENT '回复人ID' ,

  `InReplyToScreenName` VARCHAR(60) NULL COMMENT '回复人昵称' ,

  `RetweetedStatus` BIGINT NULL COMMENT '转发Status id' ,

  `RepostsCount` INT NULL COMMENT '转发数' ,

  `CommentsCount` INT NULL COMMENT '评论数' ,

  `UID` CHAR(15) NULL COMMENT '用户UID' ,

  `Name` VARCHAR(60) NULL COMMENT '微博昵称' ,

  `Province` INT NULL COMMENT '省份编码' ,

  `City` INT NULL COMMENT '城市编码' ,

  `Location` VARCHAR(80) NULL COMMENT '地址' ,

  `Description` VARCHAR(140) NULL COMMENT '个人描述' ,

  `Gender` CHAR NULL COMMENT '性别,m--男，f--女,n--未知' ,

  `FollowersCount` INT NULL COMMENT '粉丝数' ,

  `FriendsCount` INT NULL COMMENT '关注数' ,

  `StatusesCount` INT NULL COMMENT '微博数' ,

  `BiFollowersCount` INT NULL COMMENT '互粉数' ,

  `UserCreatedAt` DATETIME NULL COMMENT '用户创建时间' ,

  `Verified` CHAR NULL COMMENT '加V, y—是，n—否' ,

  `VerifiedType` INT NULL COMMENT '认证类型' ,

  `VerifiedReason` VARCHAR(100) NULL COMMENT '认证原因' ,

  PRIMARY KEY (`SID`) )

COMMENT = '抓取微博内容存放数据库';

