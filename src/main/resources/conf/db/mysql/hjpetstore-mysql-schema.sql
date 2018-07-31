-- This file can be used for database initialize of this project,
-- alternative, we are using Hibernate auto-generate the DB Schema.

-- run as root --
-- mysql -h localhost -u root -p


use hjpetstore;


delimiter $$

CREATE TABLE `Address` (
  `addressId` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `addr1` varchar(80) NOT NULL,
  `addr2` varchar(40) NOT NULL,
  `city` varchar(80) NOT NULL,
  `state` varchar(80) NOT NULL,
  `zipcode` varchar(20) NOT NULL,
  `country` varchar(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`addressId`),
  KEY `fk_address_userId` (`userId`),
  CONSTRAINT `fk_address_userId` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='The user address for billing and shipping.'$$

delimiter $$

CREATE TABLE `Banner` (
  `bannerId` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `bannerName` varchar(255) DEFAULT NULL,
  `categoryId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`bannerId`),
  UNIQUE KEY `categoryId` (`categoryId`),
  KEY `fk_banner_categoryId` (`categoryId`),
  CONSTRAINT `fk_banner_categoryId` FOREIGN KEY (`categoryId`) REFERENCES `Category` (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='The banner for product category.'$$

delimiter $$

CREATE TABLE `Category` (
  `categoryId` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `categoryName` varchar(80) NOT NULL,
  `categoryDesc` varchar(255) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`categoryId`),
  UNIQUE KEY `categoryName` (`categoryName`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='The product category'$$

delimiter $$

CREATE TABLE `Inventory` (
  `InventoryId` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`InventoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='Item inventory'$$

delimiter $$

CREATE TABLE `Item` (
  `itemId` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `itemName` varchar(10) NOT NULL,
  `listPrice` decimal(10,2) DEFAULT NULL,
  `unitCost` decimal(10,2) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `attr1` varchar(80) DEFAULT NULL,
  `attr2` varchar(80) DEFAULT NULL,
  `attr3` varchar(80) DEFAULT NULL,
  `attr4` varchar(80) DEFAULT NULL,
  `attr5` varchar(80) DEFAULT NULL,
  `productId` bigint(20) NOT NULL,
  `inventoryId` bigint(20) NOT NULL,
  `supplierId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`itemId`),
  UNIQUE KEY `itemName` (`itemName`),
  UNIQUE KEY `inventoryId` (`inventoryId`),
  KEY `fk_item_inventoryId` (`inventoryId`),
  KEY `fk_item_productId` (`productId`),
  KEY `fk_item_supplierId` (`supplierId`),
  CONSTRAINT `fk_item_supplierId` FOREIGN KEY (`supplierId`) REFERENCES `Supplier` (`supplierId`),
  CONSTRAINT `fk_item_inventoryId` FOREIGN KEY (`inventoryId`) REFERENCES `Inventory` (`InventoryId`),
  CONSTRAINT `fk_item_productId` FOREIGN KEY (`productId`) REFERENCES `Product` (`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='The item or artical that a shop sells'$$

delimiter $$

CREATE TABLE `OrderLineItem` (
  `lineNumber` int(11) NOT NULL,
  `orderId` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unitPrice` decimal(10,2) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `itemId` bigint(20) NOT NULL,
  PRIMARY KEY (`lineNumber`,`orderId`),
  KEY `fk_oli_itemId` (`itemId`),
  KEY `fk_oli_orderId` (`orderId`),
  CONSTRAINT `fk_oli_orderId` FOREIGN KEY (`orderId`) REFERENCES `Orders` (`orderId`),
  CONSTRAINT `fk_oli_itemId` FOREIGN KEY (`itemId`) REFERENCES `Item` (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='An orderItem in the order'$$

delimiter $$

CREATE TABLE `Orders` (
  `orderId` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `shipAddressId` bigint(20) NOT NULL,
  `billAddressId` bigint(20) NOT NULL,
  `courier` varchar(80) NOT NULL,
  `totalPrice` decimal(10,2) DEFAULT NULL,
  `billToFirstname` varchar(80) NOT NULL,
  `billToLastname` varchar(80) NOT NULL,
  `shipToFirstname` varchar(80) NOT NULL,
  `shipToLastname` varchar(80) NOT NULL,
  `cardNumber` varchar(200) NOT NULL,
  `expireDate` datetime NOT NULL,
  `cardType` varchar(80) NOT NULL,
  `orderStatus` varchar(20) NOT NULL,
  `locale` varchar(5) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `paymentPartnerId` bigint(20) NOT NULL,
  PRIMARY KEY (`orderId`),
  KEY `fk_order_paymentPartnerId` (`paymentPartnerId`),
  KEY `fk_order_billAddressId` (`billAddressId`),
  KEY `fk_order_shipAddressId` (`shipAddressId`),
  KEY `fk_order_userId` (`userId`),
  CONSTRAINT `fk_order_userId` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`),
  CONSTRAINT `fk_order_billAddressId` FOREIGN KEY (`billAddressId`) REFERENCES `Address` (`addressId`),
  CONSTRAINT `fk_order_paymentPartnerId` FOREIGN KEY (`paymentPartnerId`) REFERENCES `PaymentPartner` (`paymentPartnerId`),
  CONSTRAINT `fk_order_shipAddressId` FOREIGN KEY (`shipAddressId`) REFERENCES `Address` (`addressId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='The order or purchase that the use make in the shop'$$

delimiter $$

CREATE TABLE `PaymentPartner` (
  `paymentPartnerId` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `name` varchar(80) NOT NULL,
  `status` varchar(20) NOT NULL,
  `publicKey` longtext NOT NULL,
  `phone` varchar(80) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`paymentPartnerId`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='the partner of payment system'$$

delimiter $$

CREATE TABLE `Product` (
  `productId` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `productNumber` varchar(10) NOT NULL,
  `productName` varchar(80) DEFAULT NULL,
  `productDesc` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `categoryId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`productId`),
  UNIQUE KEY `productNumber` (`productNumber`),
  KEY `fk_product_categoryId` (`categoryId`),
  CONSTRAINT `fk_product_categoryId` FOREIGN KEY (`categoryId`) REFERENCES `Category` (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='The product in the stock.'$$

delimiter $$

CREATE TABLE `RSAKey` (
  `keyId` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `publicKey` longtext NOT NULL,
  `privateKey` longtext NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`keyId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='The public and private key pair used in hjpetstore.'$$

delimiter $$

CREATE TABLE `Role` (
  `roleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `roleName` varchar(20) NOT NULL,
  `roleDescription` varchar(200) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`roleId`),
  UNIQUE KEY `roleName` (`roleName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='The user role or group of this application.'$$

delimiter $$

CREATE TABLE `RoleMember` (
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`roleId`,`userId`),
  KEY `fk_user_roleId` (`roleId`),
  KEY `fk_role_userId` (`userId`),
  CONSTRAINT `fk_role_userId` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`),
  CONSTRAINT `fk_user_roleId` FOREIGN KEY (`roleId`) REFERENCES `Role` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `Supplier` (
  `supplierId` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `supplierName` varchar(80) NOT NULL,
  `status` varchar(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`supplierId`),
  UNIQUE KEY `supplierName` (`supplierName`),
  UNIQUE KEY `userId` (`userId`),
  KEY `fk_supplier_userId` (`userId`),
  CONSTRAINT `fk_supplier_userId` FOREIGN KEY (`userId`) REFERENCES `User` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='the items supplier.'$$

delimiter $$

CREATE TABLE `User` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(128) NOT NULL,
  `apiKey` longtext COMMENT 'a key for public web service access',
  `secretKey` longtext COMMENT 'the private key for web service access',
  `firstname` varchar(80) NOT NULL,
  `lastname` varchar(80) NOT NULL,
  `email` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  `phone` varchar(80) NOT NULL,
  `langPreference` varchar(80) NOT NULL,
  `favCategoryId` bigint(20) DEFAULT NULL,
  `displayMylist` bit(1) DEFAULT NULL,
  `displayBanner` bit(1) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `username` (`username`),
  KEY `fk_user_favCategoryId` (`favCategoryId`),
  CONSTRAINT `fk_user_favCategoryId` FOREIGN KEY (`favCategoryId`) REFERENCES `Category` (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='The user of this application.'$$


commit;
