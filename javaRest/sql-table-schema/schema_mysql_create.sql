CREATE TABLE `Departments` (
	`Id` INT NOT NULL AUTO_INCREMENT,
	`Name` VARCHAR(255) NOT NULL,
	`Description` VARCHAR(255),
	PRIMARY KEY (`Id`)
);

CREATE TABLE `Category` (
	`Id` INT NOT NULL AUTO_INCREMENT,
	`DepartmentId` INT NOT NULL AUTO_INCREMENT,
	`Name` VARCHAR(255) NOT NULL,
	`Description` VARCHAR(255),
	PRIMARY KEY (`Id`)
);

CREATE TABLE `Products` (
	`Id` INT NOT NULL AUTO_INCREMENT,
	`CategoryId` INT NOT NULL AUTO_INCREMENT,
	`Name` VARCHAR(255) NOT NULL,
	`Description` VARCHAR(255),
	`CostPerUnit` INT NOT NULL,
	`Quantity` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`Id`)
);

CREATE TABLE `Cart` (
	`CartId` INT NOT NULL,
	`ProductId` INT NOT NULL,
	`Quantity` INT NOT NULL
);

ALTER TABLE `Category` ADD CONSTRAINT `Category_fk0` FOREIGN KEY (`DepartmentId`) REFERENCES `Departments`(`Id`);

ALTER TABLE `Products` ADD CONSTRAINT `Products_fk0` FOREIGN KEY (`CategoryId`) REFERENCES `Category`(`Id`);

ALTER TABLE `Cart` ADD CONSTRAINT `Cart_fk0` FOREIGN KEY (`ProductId`) REFERENCES `Products`(`Id`);

