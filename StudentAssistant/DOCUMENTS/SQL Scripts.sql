-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema schedulebuilder
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema schedulebuilder
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `schedulebuilder` DEFAULT CHARACTER SET utf8mb3 ;
USE `schedulebuilder` ;

-- -----------------------------------------------------
-- Table `schedulebuilder`.`semesters`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulebuilder`.`semesters` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `startdate` DATE NOT NULL,
  `enddate` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb3
COMMENT = '		';


-- -----------------------------------------------------
-- Table `schedulebuilder`.`courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulebuilder`.`courses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `hours` INT NOT NULL,
  `grade` INT NULL DEFAULT NULL,
  `letterGrade` VARCHAR(4) NULL DEFAULT NULL,
  `semesterId` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `courseSemester_idx` (`semesterId` ASC) VISIBLE,
  CONSTRAINT `courseSemester`
    FOREIGN KEY (`semesterId`)
    REFERENCES `schedulebuilder`.`semesters` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `schedulebuilder`.`assignments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulebuilder`.`assignments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `grade` DOUBLE NOT NULL,
  `deadline` DATE NULL DEFAULT NULL,
  `courseId` INT NULL DEFAULT NULL,
  `receivedgrade` VARCHAR(45) NULL DEFAULT NULL,
  `addedtotasks` VARCHAR(45) NOT NULL DEFAULT 'NO',
  PRIMARY KEY (`id`),
  INDEX `assignmentCourse_idx` (`courseId` ASC) VISIBLE,
  CONSTRAINT `assignmentCourse`
    FOREIGN KEY (`courseId`)
    REFERENCES `schedulebuilder`.`courses` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 69
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `schedulebuilder`.`classes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulebuilder`.`classes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `day` VARCHAR(45) NOT NULL,
  `time` VARCHAR(45) NOT NULL,
  `courseid` INT NOT NULL,
  `length` INT NOT NULL,
  `room` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 23
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `schedulebuilder`.`completedassignments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulebuilder`.`completedassignments` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `grade` DOUBLE NULL DEFAULT NULL,
  `receivedGrade` VARCHAR(5) NULL DEFAULT NULL,
  `courseId` INT NULL DEFAULT NULL,
  `deadline` DATE NULL DEFAULT NULL,
  `submitdate` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `completedAssignmentCourse_idx` (`courseId` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `schedulebuilder`.`completedtasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulebuilder`.`completedtasks` (
  `id` VARCHAR(50) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `assignmentid` VARCHAR(45) NULL DEFAULT NULL,
  `length` INT NULL DEFAULT NULL,
  `time` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `schedulebuilder`.`tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulebuilder`.`tasks` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `assignmentid` INT NULL DEFAULT NULL,
  `day` VARCHAR(45) NULL DEFAULT 'UNSET',
  `time` INT NULL DEFAULT '0',
  `length` INT NULL DEFAULT '0',
  `deadline` DATE NULL DEFAULT NULL,
  `date` DATE NULL DEFAULT NULL,
  `edited` VARCHAR(45) NULL DEFAULT 'NO',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 807
DEFAULT CHARACTER SET = utf8mb3;

USE `schedulebuilder` ;

-- -----------------------------------------------------
-- Placeholder table for view `schedulebuilder`.`above85courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulebuilder`.`above85courses` (`sum(completedassignments.grade)` INT, `sum(completedassignments.receivedgrade)` INT, `name` INT, `ProjectedGrade` INT);

-- -----------------------------------------------------
-- Placeholder table for view `schedulebuilder`.`below85courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulebuilder`.`below85courses` (`sum(completedassignments.grade)` INT, `sum(completedassignments.receivedgrade)` INT, `name` INT, `grade` INT);

-- -----------------------------------------------------
-- Placeholder table for view `schedulebuilder`.`current_semester_courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulebuilder`.`current_semester_courses` (`ID` INT, `NAME` INT, `HOURS` INT, `GRADE` INT, `LETTERGRADE` INT);

-- -----------------------------------------------------
-- Placeholder table for view `schedulebuilder`.`projectedgrade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulebuilder`.`projectedgrade` (`projectedgrade` INT, `id` INT);

-- -----------------------------------------------------
-- Placeholder table for view `schedulebuilder`.`weekassignments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulebuilder`.`weekassignments` (`assignment` INT, `grade` INT, `daysleft` INT, `name` INT);

-- -----------------------------------------------------
-- procedure assignments_to_tasks
-- -----------------------------------------------------

DELIMITER $$
USE `schedulebuilder`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `assignments_to_tasks`()
BEGIN
INSERT INTO tasks (assignmentid, name, deadline) 
select id, name, deadline from assignments where datediff(deadline, sysdate()) < 15 AND ADDEDTOTASKS = 'NO';
insert into tasks (assignmentid, name, deadline)
select id, name, deadline from assignments where datediff(deadline, sysdate()) < 15 and assignments.grade > 9 AND ADDEDTOTASKS = 'NO';
UPDATE TASKS SET LENGTH = (SELECT GRADE FROM ASSIGNMENTS WHERE ID = TASKS.ASSIGNMENTID)/2 where edited = 'NO';
update TASKS set length = length /2 where length > 8 and edited = 'NO';
update tasks set length = 1 where length = 0 and edited = 'NO';
update tasks set length = length /2 where length > 4 and edited = 'NO';
UPDATE TASKS SET EDITED = 'YES' WHERE EDITED = 'NO';
UPDATE ASSIGNMENTS SET ADDEDTOTASKS = 'YES' WHERE DATEDIFF(DEADLINE,SYSDATE()) < 15 ;
DELETE FROM TASKS WHERE DATEDIFF(DATE,SYSDATE()) < 0;
end$$

DELIMITER ;

-- -----------------------------------------------------
-- View `schedulebuilder`.`above85courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `schedulebuilder`.`above85courses`;
USE `schedulebuilder`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `schedulebuilder`.`above85courses` AS select sum(`schedulebuilder`.`completedassignments`.`grade`) AS `sum(completedassignments.grade)`,sum(`schedulebuilder`.`completedassignments`.`receivedGrade`) AS `sum(completedassignments.receivedgrade)`,`schedulebuilder`.`courses`.`name` AS `name`,((sum(`schedulebuilder`.`completedassignments`.`receivedGrade`) / sum(`schedulebuilder`.`completedassignments`.`grade`)) * 100) AS `ProjectedGrade` from (`schedulebuilder`.`completedassignments` left join `schedulebuilder`.`courses` on((`schedulebuilder`.`courses`.`id` = `schedulebuilder`.`completedassignments`.`courseId`))) where (`schedulebuilder`.`completedassignments`.`receivedGrade` is not null) group by `schedulebuilder`.`completedassignments`.`courseId` having (`ProjectedGrade` < 85);

-- -----------------------------------------------------
-- View `schedulebuilder`.`below85courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `schedulebuilder`.`below85courses`;
USE `schedulebuilder`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `schedulebuilder`.`below85courses` AS select sum(`schedulebuilder`.`completedassignments`.`grade`) AS `sum(completedassignments.grade)`,sum(`schedulebuilder`.`completedassignments`.`receivedGrade`) AS `sum(completedassignments.receivedgrade)`,`schedulebuilder`.`courses`.`name` AS `name`,round(`schedulebuilder`.`projectedgrade`.`projectedgrade`,1) AS `grade` from ((`schedulebuilder`.`completedassignments` left join `schedulebuilder`.`courses` on((`schedulebuilder`.`courses`.`id` = `schedulebuilder`.`completedassignments`.`courseId`))) left join `schedulebuilder`.`projectedgrade` on((`schedulebuilder`.`courses`.`id` = `schedulebuilder`.`projectedgrade`.`id`))) where (`schedulebuilder`.`completedassignments`.`receivedGrade` is not null) group by `schedulebuilder`.`courses`.`id` having (`grade` > 85);

-- -----------------------------------------------------
-- View `schedulebuilder`.`current_semester_courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `schedulebuilder`.`current_semester_courses`;
USE `schedulebuilder`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `schedulebuilder`.`current_semester_courses` AS select `schedulebuilder`.`courses`.`id` AS `ID`,`schedulebuilder`.`courses`.`name` AS `NAME`,`schedulebuilder`.`courses`.`hours` AS `HOURS`,`schedulebuilder`.`courses`.`grade` AS `GRADE`,`schedulebuilder`.`courses`.`letterGrade` AS `LETTERGRADE` from (`schedulebuilder`.`courses` left join `schedulebuilder`.`semesters` on((`schedulebuilder`.`courses`.`semesterId` = `schedulebuilder`.`semesters`.`id`))) where ((`schedulebuilder`.`semesters`.`startdate` < curdate()) and (curdate() < `schedulebuilder`.`semesters`.`enddate`));

-- -----------------------------------------------------
-- View `schedulebuilder`.`projectedgrade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `schedulebuilder`.`projectedgrade`;
USE `schedulebuilder`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `schedulebuilder`.`projectedgrade` AS select ((sum(`schedulebuilder`.`completedassignments`.`receivedGrade`) / sum(`schedulebuilder`.`completedassignments`.`grade`)) * 100) AS `projectedgrade`,`schedulebuilder`.`courses`.`id` AS `id` from (`schedulebuilder`.`completedassignments` left join `schedulebuilder`.`courses` on((`schedulebuilder`.`completedassignments`.`courseId` = `schedulebuilder`.`courses`.`id`))) where (`schedulebuilder`.`completedassignments`.`receivedGrade` is not null) group by `schedulebuilder`.`completedassignments`.`courseId`;

-- -----------------------------------------------------
-- View `schedulebuilder`.`weekassignments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `schedulebuilder`.`weekassignments`;
USE `schedulebuilder`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `schedulebuilder`.`weekassignments` AS select `schedulebuilder`.`assignments`.`name` AS `assignment`,`schedulebuilder`.`assignments`.`grade` AS `grade`,(to_days(`schedulebuilder`.`assignments`.`deadline`) - to_days(curdate())) AS `daysleft`,`schedulebuilder`.`courses`.`name` AS `name` from (`schedulebuilder`.`assignments` left join `schedulebuilder`.`courses` on((`schedulebuilder`.`courses`.`id` = `schedulebuilder`.`assignments`.`courseId`))) where ((to_days(`schedulebuilder`.`assignments`.`deadline`) - to_days(curdate())) < 8) order by (to_days(`schedulebuilder`.`assignments`.`deadline`) - to_days(curdate()));
USE `schedulebuilder`;

DELIMITER $$
USE `schedulebuilder`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `schedulebuilder`.`ADD_DATE`
BEFORE INSERT ON `schedulebuilder`.`completedassignments`
FOR EACH ROW
BEGIN
set new.submitdate = curdate();
END$$

USE `schedulebuilder`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `schedulebuilder`.`REMOVE_ASSIGNMENT_FROM_TASKS`
BEFORE INSERT ON `schedulebuilder`.`completedassignments`
FOR EACH ROW
BEGIN
DELETE FROM TASKS WHERE ASSIGNMENTID = NEW.ID;
END$$

USE `schedulebuilder`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `schedulebuilder`.`before_update_invalid_grade`
BEFORE UPDATE ON `schedulebuilder`.`completedassignments`
FOR EACH ROW
begin
IF (NEW.RECEIVEDGRADE > new.GRADE) THEN
SIGNAL SQLSTATE '02000' SET MESSAGE_TEXT = 'Warning: received grade entered > total assignment grade';
end if;
end$$

USE `schedulebuilder`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `schedulebuilder`.`completedtasks_AFTER_INSERT_date`
BEFORE INSERT ON `schedulebuilder`.`completedtasks`
FOR EACH ROW
set new.time = curdate();$$

USE `schedulebuilder`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `schedulebuilder`.`ADD_DAY_FROM_DATE`
BEFORE INSERT ON `schedulebuilder`.`tasks`
FOR EACH ROW
BEGIN
IF(NEW.DATE IS NOT NULL) THEN
set new.day = DAYNAME(new.date); END IF;
END$$

USE `schedulebuilder`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `schedulebuilder`.`ADD_DAY_FROM_DATE_AFTER_UPDATE`
BEFORE UPDATE ON `schedulebuilder`.`tasks`
FOR EACH ROW
BEGIN
IF(NEW.DATE IS NOT NULL) THEN 
set new.day = DAYNAME(new.date); END IF;
END$$

USE `schedulebuilder`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `schedulebuilder`.`CHECK_VALID_TIME`
BEFORE UPDATE ON `schedulebuilder`.`tasks`
FOR EACH ROW
BEGIN
if (new.time + new.length > 30)
then SIGNAL SQLSTATE '02001' SET MESSAGE_TEXT = 'Warning: INVALID INPUT. TIME + LENGTH > 24';
    END IF;
    END$$

USE `schedulebuilder`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `schedulebuilder`.`INSERT_INTO_COMPLETEDTASKS`
BEFORE DELETE ON `schedulebuilder`.`tasks`
FOR EACH ROW
BEGIN
INSERT INTO COMPLETEDTASKS (ID, NAME, LENGTH, ASSIGNMENTID) VALUES (OLD.ID, OLD.NAME, OLD.LENGTH, OLD.ASSIGNMENTID);
END$$

USE `schedulebuilder`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `schedulebuilder`.`tasks_BEFORE_INSERT`
BEFORE INSERT ON `schedulebuilder`.`tasks`
FOR EACH ROW
BEGIN
if (new.time + new.length > 30)
then SIGNAL SQLSTATE '02001' SET MESSAGE_TEXT = 'Warning: INVALID INPUT. TIME + LENGTH > 24';
    END IF;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
