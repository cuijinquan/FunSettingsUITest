package com.example.zhengjin.funsettingsuitest.testcases;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.example.zhengjin.funsettingsuitest.testcategory.CategoryDemoTests;
import com.example.zhengjin.funsettingsuitest.testcategory.CategoryFileManagerTests;
import com.example.zhengjin.funsettingsuitest.testuiactions.DeviceActionCenter;
import com.example.zhengjin.funsettingsuitest.testuiactions.DeviceActionMenu;
import com.example.zhengjin.funsettingsuitest.testuiactions.DeviceActionMoveDown;
import com.example.zhengjin.funsettingsuitest.testuiactions.DeviceActionMoveLeft;
import com.example.zhengjin.funsettingsuitest.testuiactions.DeviceActionMoveRight;
import com.example.zhengjin.funsettingsuitest.testuiactions.DeviceActionMoveUp;
import com.example.zhengjin.funsettingsuitest.testuiobjects.UiObjectsFileManager;
import com.example.zhengjin.funsettingsuitest.testuitasks.TaskFileManager;
import com.example.zhengjin.funsettingsuitest.testuitasks.TaskLauncher;
import com.example.zhengjin.funsettingsuitest.testutils.ShellUtils;
import com.example.zhengjin.funsettingsuitest.testutils.TestConstants;
import com.example.zhengjin.funsettingsuitest.testutils.TestHelper;
import com.example.zhengjin.funsettingsuitest.utils.FileUtils;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;

import static com.example.zhengjin.funsettingsuitest.testutils.TestConstants.FILE_MANAGER_PKG_NAME;

/**
 * Created by zhengjin on 2016/6/7.
 * <p>
 * Include the test cases for file manager APP.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestFileManager extends TestCaseBase {

    private static final String TEST_ROOT_DIR_NAME = "AutoTestFiles";
    private static final String TEST_ROOT_DIR_PATH;
    private static final String TEST_DIR_NAME = "TestNonMediaDir";
    private static final String TEST_DIR_PATH;
    private static final String TEST_MEDIA_DIR_NAME = "TestMediaDir";
    private static final String TEST_MEDIA_DIR_PATH;
    private static final String TEST_PIC_DIR_NAME = "TestPicDir";
    private static final String TEST_PIC_DIR_PATH;

    private static final String TEST1_FILE_NAME = "TestFile1.log";
    private static final String TEST1_FILE_PATH;
    private static final String TEST2_FILE_NAME = "TestFile2.log";
    private static final String TEST2_FILE_PATH;
    private static final String TEST1_VIDEO_FILE_NAME = "TestVideo1.mp4";
    private static final String TEST1_VIDEO_FILE_PATH;
    private static final String TEST2_VIDEO_FILE_NAME = "TestVideo2.mp4";
    private static final String TEST2_VIDEO_FILE_PATH;

    private final String TEXT_REMOVE_BUTTON = "删除";
    private final String TEXT_HIDDEN_BUTTON = "隐藏";
    private final String TEXT_SHOWALL_BUTTON = "显示全部";

    private final String MESSAGE_TEXT_NO_FILES_FOUND = "未发现可显示的文件";
    private final String MESSAGE_TEXT_NO_VIDEO_FOUND = "未发现可播放的视频";
    private final String MESSAGE_TEXT_NO_PIC_FOUND = "未发现可显示的图片";

    private final UiObjectsFileManager mFunUiObjects = UiObjectsFileManager.getInstance();
    private final TaskFileManager mTask = TaskFileManager.getInstance();

    private String mMessage;

    static {
        String SDCARD_PATH = "";
        try {
            SDCARD_PATH = FileUtils.getExternalStoragePath();
        } catch (IOException e) {
            e.printStackTrace();
            // Assert failed in static block, then all test methods will run failed
            Assert.assertTrue(e.getMessage(), false);
        }

        TEST_ROOT_DIR_PATH = SDCARD_PATH + File.separator + TEST_ROOT_DIR_NAME;

        TEST_DIR_PATH = TEST_ROOT_DIR_PATH + File.separator + TEST_DIR_NAME;
        TEST1_FILE_PATH = TEST_ROOT_DIR_PATH + File.separator + TEST1_FILE_NAME;
        TEST2_FILE_PATH = TEST_DIR_PATH + File.separator + TEST2_FILE_NAME;

        TEST_MEDIA_DIR_PATH = TEST_ROOT_DIR_PATH + File.separator + TEST_MEDIA_DIR_NAME;
        TEST1_VIDEO_FILE_PATH = TEST_MEDIA_DIR_PATH + File.separator + TEST1_VIDEO_FILE_NAME;
        TEST2_VIDEO_FILE_PATH = TEST_MEDIA_DIR_PATH + File.separator + TEST2_VIDEO_FILE_NAME;

        TEST_PIC_DIR_PATH = TEST_ROOT_DIR_PATH + File.separator + TEST_PIC_DIR_NAME;
    }

    @BeforeClass
    public static void classSetUp() {
        ShellUtils.stopAndClearPackage(FILE_MANAGER_PKG_NAME);
        prepareData();
    }

    @AfterClass
    public static void classClearUp() {
        removeData();
    }

    private static void prepareData() {
        String message = "Prepare files for file manager test.";
        String cmdCreateTestDir = "mkdir -p " + TEST_DIR_PATH;
        String cmdCreateTxtFile1 = "touch " + TEST1_FILE_PATH;
        String cmdCreateTxtFile2 = "touch " + TEST2_FILE_PATH;

        String cmdCreateMediaDir = "mkdir -p " + TEST_MEDIA_DIR_PATH;
        String cmdCreateVideoFile1 = "touch " + TEST1_VIDEO_FILE_PATH;
        String cmdCreateVideoFile2 = "touch " + TEST2_VIDEO_FILE_PATH;

        String commands[] = {cmdCreateTestDir, cmdCreateTxtFile1, cmdCreateTxtFile2,
                cmdCreateMediaDir, cmdCreateVideoFile1, cmdCreateVideoFile2};

        ShellUtils.CommandResult result = ShellUtils.execCommand(commands, false, false);
        Assert.assertTrue(message, (result.mReturnCode == 0));
    }

    private static void removeData() {
        String message = "Clear files for file manager test.";
        String removeAllFiles = String.format("rm -rf %s", TEST_ROOT_DIR_PATH);

        ShellUtils.CommandResult result = ShellUtils.execCommand(removeAllFiles, false, false);
        Assert.assertTrue(message, (result.mReturnCode == 0));
    }

    @Before
    public void setUp() {
        TaskLauncher.backToLauncher();
        mTask.openFileManagerHomePage();
    }

    @After
    public void clearUp() {
        ShellUtils.stopProcessByPackageName(FILE_MANAGER_PKG_NAME);
    }

    @Test
    @Ignore
    @Category(CategoryFileManagerTests.class)
    public void test11FileManagerHomeTabShow() {
        mMessage = "Verify the sdcard tab name is enabled.";
        UiObject2 tabContainer = mDevice.findObject(mFunUiObjects.getFileManagerHomeTabSelector());
        Assert.assertNotNull(tabContainer);
        Assert.assertTrue(mMessage, TestHelper.waitForUiObjectEnabled(tabContainer));
    }

    @Test
    @Category({CategoryFileManagerTests.class})
    public void test13OpenAllFilesCardFromSdcardTab() {
        mTask.openLocalFilesCard();

        mMessage = "Verify the text of main title from sdcard local files.";
        UiObject2 mainTitle = mDevice.findObject(mFunUiObjects.getMainTitleSelector());
        Assert.assertNotNull(mainTitle);
        Assert.assertEquals(mMessage, "本地存储", mainTitle.getText());

        mMessage = "Verify the text of sub title from sdcard local files.";
        UiObject2 subTitle = mDevice.findObject(mFunUiObjects.getSubTitleSelector());
        Assert.assertEquals(mMessage, "全部文件", subTitle.getText());
    }

    @Test
    @Category({CategoryFileManagerTests.class})
    public void test14NavigateToSpecifiedPath() {
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);

        mMessage = "Verify navigate to the specified path.";
        UiObject2 subTitle = mDevice.findObject(mFunUiObjects.getSubTitleSelector());
        Assert.assertEquals(mMessage, TEST_ROOT_DIR_NAME, subTitle.getText());
    }

    @Test
    @Ignore
    @Category(CategoryFileManagerTests.class)
    public void test15OpenSpecifiedPicture() {
        // pre-condition: push pic file to the device
        // TODO: 2016/11/21
    }

    @Test
    @Category({CategoryFileManagerTests.class})
    public void test16OpenUnknownTypeFile() {
        mTask.openLocalFilesCard();

        mMessage = "Verify open the unknown type file.";
        mTask.navigateAndOpenSpecifiedFile(TEST1_FILE_PATH);
        Assert.assertTrue(mMessage, TestHelper.waitForUiObjectExist(By.text(TEST1_FILE_NAME)));
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test21MenuHideBtnExistForDir() {
        mTask.openLocalFilesCard();

        // verification 1
        mMessage = "Verify the menu tips is displayed.";
        UiObject2 menuTips = mDevice.findObject(mFunUiObjects.getMenuTipsSelector());
        Assert.assertTrue(mMessage, menuTips.getText().contains("查看更多操作"));

        // verification 2
        mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
        mAction.doChainedDeviceActionAndWait(new DeviceActionMoveRight())
                .doDeviceActionAndWait(new DeviceActionMoveLeft());  // request focus
        mTask.showMenuAndRequestFocus();

        mMessage = "Verify the hide button is focused in the bottom menu.";
        UiObject2 menuHideBtnContainer =
                mDevice.findObject(mFunUiObjects.getMenuHideBtnContainerSelector());
        Assert.assertNotNull(menuHideBtnContainer);
        Assert.assertTrue(mMessage, menuHideBtnContainer.isFocused());

        mMessage = "Verify the text of hide button in the bottom menu.";
        UiObject2 menuHideBtn =
                menuHideBtnContainer.findObject(mFunUiObjects.getMenuBtnTextSelector());
        Assert.assertNotNull(menuHideBtn);
        Assert.assertEquals(mMessage, TEXT_HIDDEN_BUTTON, menuHideBtn.getText());
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test22MenuRemoveAndHideBtnExistForFile() {
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
        mAction.doRepeatDeviceActionAndWait(new DeviceActionMoveRight(), 2);  // request focus
        mTask.showMenuAndRequestFocus();

        // verification 1
        mMessage = "Verify the remove button is focused in the bottom menu.";
        UiObject2 menuRemoveBtnContainer =
                mDevice.findObject(mFunUiObjects.getMenuRemoveBtnContainerSelector());
        Assert.assertNotNull(menuRemoveBtnContainer);
        Assert.assertTrue(mMessage, menuRemoveBtnContainer.isFocused());

        mMessage = "Verify the text of remove button in the bottom menu.";
        UiObject2 menuRemoveBtn =
                menuRemoveBtnContainer.findObject(mFunUiObjects.getMenuBtnTextSelector());
        Assert.assertEquals(mMessage, TEXT_REMOVE_BUTTON, menuRemoveBtn.getText());

        // verification 2
        mMessage = "Verify the text of hide button in the bottom menu.";
        UiObject2 menuHideBtnContainer =
                mDevice.findObject(mFunUiObjects.getMenuHideBtnContainerSelector());
        UiObject2 menuHideBtn =
                menuHideBtnContainer.findObject(mFunUiObjects.getMenuBtnTextSelector());
        Assert.assertEquals(mMessage, TEXT_HIDDEN_BUTTON, menuHideBtn.getText());
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test23HideAndShowDirectory() {
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
        mAction.doChainedDeviceActionAndWait(new DeviceActionMoveUp())
                .doRepeatDeviceActionAndWait(new DeviceActionMoveLeft(), 2);  // request focus

        mMessage = "Verify the directory is hidden after click Hide button.";
        mTask.showMenuAndClickBtn(TEXT_HIDDEN_BUTTON);
        UiObject2 fileHidden = mDevice.findObject(By.text(TEST_DIR_NAME));
        Assert.assertNull(mMessage, fileHidden);

        mMessage = "Verify the Show All button is displayed in menu after hide file.";
        mTask.showMenuAndRequestFocus();
        UiObject2 btnContainer =
                mDevice.findObject(mFunUiObjects.getMenuShowAllBtnContainerSelector());
        UiObject2 btnShowAll = btnContainer.findObject(mFunUiObjects.getMenuBtnTextSelector());
        Assert.assertEquals(mMessage, TEXT_SHOWALL_BUTTON, btnShowAll.getText());

        mMessage = "Verify the directory is show after click Show All button.";
        mTask.showMenuAndClickBtn(TEXT_SHOWALL_BUTTON);
        UiObject2 fileShow = mDevice.findObject(By.text(TEST_DIR_NAME));
        Assert.assertNotNull(mMessage, fileShow);
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test24HideAndShowFile() {
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
        mAction.doRepeatDeviceActionAndWait(new DeviceActionMoveRight(), 2);  // request focus

        mMessage = "Verify the file is hidden after click Hide button.";
        mTask.showMenuAndClickBtn(TEXT_HIDDEN_BUTTON);
        UiObject2 fileHidden = mDevice.findObject(By.text(TEST1_FILE_NAME));
        Assert.assertNull(mMessage, fileHidden);

        mMessage = "Verify the file is show after click Show All button.";
        mTask.showMenuAndClickBtn(TEXT_SHOWALL_BUTTON);
        UiObject2 fileShow = mDevice.findObject(By.text(TEST1_FILE_NAME));
        Assert.assertNotNull(mMessage, fileShow);
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test25RemoveFileAndCancel() {
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
        mAction.doRepeatDeviceActionAndWait(new DeviceActionMoveRight(), 2);  // request focus
        mTask.showMenuAndClickBtn(TEXT_REMOVE_BUTTON);

        mMessage = "Verify the Cancel button of confirm dialog.";
        UiObject2 cancelBtn =
                mDevice.findObject(mFunUiObjects.getCancelBtnOfConfirmDialogSelector());
        Assert.assertNotNull(mMessage, cancelBtn);

        mMessage = "Verify click cancel and do not remove a file.";
        mAction.doClickActionAndWait(cancelBtn);
        UiObject2 fileDeleted = mDevice.findObject(By.text(TEST1_FILE_NAME));
        Assert.assertNotNull(mMessage, fileDeleted);
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test26RemoveFileAndConfirm() {
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
        mAction.doRepeatDeviceActionAndWait(new DeviceActionMoveRight(), 2);  // request focus
        mTask.showMenuAndClickBtn(TEXT_REMOVE_BUTTON);

        mMessage = "Verify the Yes button of confirm dialog.";
        UiObject2 confirmBtn = mDevice.findObject(mFunUiObjects.getYesBtnOfConfirmDialogSelector());
        Assert.assertNotNull(mMessage, confirmBtn);

        mMessage = "Verify click yes and remove a file.";
        mAction.doClickActionAndWait(confirmBtn);
        UiObject2 fileDeleted = mDevice.findObject(By.text(TEST1_FILE_NAME));
        Assert.assertNull(mMessage, fileDeleted);
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test27HideAndShowOnlyFileOfDirectory() {
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(TEST_DIR_PATH);
        mAction.doDeviceActionAndWait(new DeviceActionMoveUp());  // request focus

        mTask.showMenuAndClickBtn(TEXT_HIDDEN_BUTTON);
        mMessage = "Verify the tips of empty directory from all files card.";
        UiObject2 tips = mDevice.findObject(
                mFunUiObjects.getTipsOfEmptyDirFromLocalFilesCardSelector());
        Assert.assertNotNull(tips);
        Assert.assertEquals(mMessage, MESSAGE_TEXT_NO_FILES_FOUND, tips.getText());

        mMessage = "Verify the file is show after click Show All.";
        mTask.showMenuAndClickBtn(TEXT_SHOWALL_BUTTON);
        UiObject2 hiddenFile = mDevice.findObject(By.text(TEST2_FILE_NAME));
        Assert.assertTrue(mMessage, TestHelper.waitForUiObjectEnabled(hiddenFile));
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test28RemoveOnlyFileOfDirectory() {
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(TEST_DIR_PATH);

        // remove file
        mAction.doDeviceActionAndWait(new DeviceActionMoveUp());  // request focus
        mTask.showMenuAndClickBtn(TEXT_REMOVE_BUTTON);
        UiObject2 confirmBtn = mDevice.findObject(mFunUiObjects.getYesBtnOfConfirmDialogSelector());
        mAction.doClickActionAndWait(confirmBtn);

        mMessage = "Verify the tips of empty directory from all files card.";
        UiObject2 tips = mDevice.findObject(
                mFunUiObjects.getTipsOfEmptyDirFromLocalFilesCardSelector());
        Assert.assertNotNull(tips);
        Assert.assertEquals(mMessage, MESSAGE_TEXT_NO_FILES_FOUND, tips.getText());
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test31MessageWhenEmptyForAppCard() {
        final String MESSAGE_TEXT_NO_APPS_FOUND = "未发现可安装的应用";

        mMessage = "Verify the tips when no files in APP card.";
        mTask.openCategoryAppCard();
        UiObject2 tips = mDevice.findObject(
                mFunUiObjects.getTipsOfEmptyDirFromLocalFilesCardSelector());
        Assert.assertNotNull(tips);
        Assert.assertEquals(mMessage, MESSAGE_TEXT_NO_APPS_FOUND, tips.getText());

        mMessage = "Verify the menu is NOT shown when no files in APP card.";
        mAction.doDeviceActionAndWait(new DeviceActionMenu());
        UiObject2 menu = mDevice.findObject(mFunUiObjects.getMenuContainerSelector());
        Assert.assertNull(mMessage, menu);
    }

    @Test
    @Ignore
    @Category(CategoryFileManagerTests.class)
    public void test32VideoCardNameAndItemsCount() {
        // Error: obtaining UI hierarchy
        mDevice.waitForIdle();

        mMessage = "Verify the video card is enabled.";
        UiObject2 videoCardContainer = mDevice.findObject(mFunUiObjects.getVideoCategorySelector());
        Assert.assertTrue(mMessage, TestHelper.waitForUiObjectEnabled(videoCardContainer));

        mMessage = "Verify the text of video card.";
        UiObject2 videoCardText =
                videoCardContainer.findObject(mFunUiObjects.getCategoryTitleSelector());
        Assert.assertNotNull(videoCardText);
        Assert.assertEquals(mMessage, "视频", videoCardText.getText());

        mMessage = "Verify the files count of video card.";
        UiObject2 videoCardCount =
                videoCardContainer.findObject(mFunUiObjects.getCategoryEntiesCountSelector());
        Assert.assertNotNull(videoCardCount);
        Assert.assertEquals(mMessage, "(0项)", videoCardCount.getText());
    }

    @Test
    @Category({CategoryFileManagerTests.class})
    public void test41_01HideVideoFile() {
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(TEST_MEDIA_DIR_PATH);
        mTask.moveUntilSpecifiedItemSelected(TEST1_VIDEO_FILE_NAME);  // request focus

        mMessage = "Verify the video file is hidden after click Hide button.";
        mTask.showMenuAndClickBtn(TEXT_HIDDEN_BUTTON);
        UiObject2 fileHiddenFromAll = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
        Assert.assertNull(mMessage, fileHiddenFromAll);

        this.backToFileManagerHome();
        mTask.openCategoryVideoCard();
        mTask.navigateToSpecifiedPath(TEST_MEDIA_DIR_NAME);

        mMessage = "Verify the video file is hidden from video category.";
        UiObject2 fileHiddenFromCate = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
        Assert.assertNull(mMessage, fileHiddenFromCate);

        mMessage = "Verify the unchanged video file is shown from video category.";
        UiObject2 fileUnchanged = mDevice.findObject(By.text(TEST2_VIDEO_FILE_NAME));
        Assert.assertNotNull(mMessage, fileUnchanged);
    }

    @Test
    @Category({CategoryFileManagerTests.class})
    public void test41_02ShowVideoFile() {
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(TEST_MEDIA_DIR_PATH);

        mMessage = "Verify the video file is shown after click Show All button.";
        mTask.showMenuAndClickBtn(TEXT_SHOWALL_BUTTON);
        UiObject2 fileShownFromAll = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
        Assert.assertTrue(mMessage, TestHelper.waitForUiObjectEnabled(fileShownFromAll));

        this.backToFileManagerHome();
        mTask.openCategoryVideoCard();
        mTask.navigateToSpecifiedPath(TEST_MEDIA_DIR_NAME);

        mMessage = "Verify the video file is shown from video category.";
        UiObject2 fileShownFromCate = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
        Assert.assertTrue(mMessage, TestHelper.waitForUiObjectEnabled(fileShownFromCate));

        mMessage = "Verify the unchanged video file is shown from video category.";
        UiObject2 fileUnchanged = mDevice.findObject(By.text(TEST2_VIDEO_FILE_NAME));
        Assert.assertNotNull(mMessage, fileUnchanged);
    }

    @Test
    @Category({CategoryFileManagerTests.class, CategoryDemoTests.class})
    public void test41_03HideVideoDirectory() {
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
        ShellUtils.takeScreenCapture(mDevice);
        mTask.moveUntilSpecifiedItemSelected(TEST_MEDIA_DIR_NAME);

        mMessage = "Verify the video directory is hidden from All Files category.";
        mTask.showMenuAndClickBtn(TEXT_HIDDEN_BUTTON);
        UiObject2 mediaDirHidden = mDevice.findObject(By.text(TEST_MEDIA_DIR_NAME));
        Assert.assertNull(mMessage, mediaDirHidden);

        this.backToFileManagerHome();
        mTask.openCategoryVideoCard();

        mMessage = "Verify all video files in directory is hidden from Video category.";
        UiObject2 tips = mDevice.findObject(
                mFunUiObjects.getTipsOfEmptyDirFromLocalFilesCardSelector());
        Assert.assertTrue(TestHelper.waitForUiObjectEnabled(tips));
        Assert.assertEquals(mMessage, MESSAGE_TEXT_NO_VIDEO_FOUND, tips.getText());
    }

    @Test
    @Category({CategoryFileManagerTests.class})
    public void test41_04ShowVideoDirectory() {
        mTask.openLocalFilesCard();
        mTask.showMenuAndClickBtn(TEXT_SHOWALL_BUTTON);
        mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);

        mMessage = "Verify the video directory is shown from All Files category.";
        UiObject2 mediaDirShown = mDevice.findObject(By.text(TEST_MEDIA_DIR_NAME));
        Assert.assertTrue(mMessage, TestHelper.waitForUiObjectEnabled(mediaDirShown));

        this.backToFileManagerHome();
        mTask.openCategoryVideoCard();
        mTask.navigateToSpecifiedPath(TEST_MEDIA_DIR_NAME);

        mMessage = "Verify all video files(%s) in directory is shown from Video category.";
        UiObject2 videoFileTest1 = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
        Assert.assertTrue(String.format(mMessage, TEST1_VIDEO_FILE_NAME),
                TestHelper.waitForUiObjectEnabled(videoFileTest1));

        UiObject2 videoFileTest2 = mDevice.findObject(By.text(TEST2_VIDEO_FILE_NAME));
        Assert.assertNotNull(String.format(mMessage, TEST2_VIDEO_FILE_NAME), videoFileTest2);
    }

    @Test
    @Category({CategoryFileManagerTests.class})
    public void test41_05RemoveVideoFileFromLocalDir() {
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(TEST_MEDIA_DIR_PATH);
        mTask.moveUntilSpecifiedItemSelected(TEST1_VIDEO_FILE_NAME);

        mMessage = "Verify the video file is removed after click Remove button.";
        this.removeFileAndConfirm();
        UiObject2 fileRemovedFromAll = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
        Assert.assertNull(mMessage, fileRemovedFromAll);

        this.backToFileManagerHome();
        mTask.openCategoryVideoCard();
        mTask.navigateToSpecifiedPath(TEST_MEDIA_DIR_NAME);

        mMessage = "Verify the video file is removed from video category.";
        UiObject2 fileRemovedFromCate = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
        Assert.assertNull(mMessage, fileRemovedFromCate);

        mMessage = "Verify the unchanged video file is shown from video category.";
        UiObject2 fileUnchanged = mDevice.findObject(By.text(TEST2_VIDEO_FILE_NAME));
        Assert.assertNotNull(mMessage, fileUnchanged);
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test41_06RemoveVideoFileFromVideoCategory() {
        mTask.openCategoryVideoCard();
        mTask.navigateToSpecifiedPath(TEST_MEDIA_DIR_NAME);
        mTask.moveUntilSpecifiedItemSelected(TEST2_VIDEO_FILE_NAME);

        mMessage = "Verify the video file is removed from video category.";
        this.removeFileAndConfirm();
        UiObject2 fileRemovedFromCate = mDevice.findObject(By.text(TEST2_VIDEO_FILE_NAME));
        Assert.assertNull(mMessage, fileRemovedFromCate);

        this.backToFileManagerHome();
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(TEST_MEDIA_DIR_PATH);

        mMessage = "Verify the video file is removed from all files category.";
        UiObject2 fileRemovedFromAll = mDevice.findObject(By.text(TEST2_VIDEO_FILE_NAME));
        Assert.assertNull(mMessage, fileRemovedFromAll);
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test41_07MessageWhenEmptyForVideoCard() {
        mTask.openCategoryVideoCard();

        mMessage = "Verify the tips when no files in video card.";
        UiObject2 tips = mDevice.findObject(
                mFunUiObjects.getTipsOfEmptyDirFromLocalFilesCardSelector());
        Assert.assertNotNull(tips);
        Assert.assertEquals(mMessage, MESSAGE_TEXT_NO_VIDEO_FOUND, tips.getText());

        mMessage = "Verify the menu is NOT shown when no files in video card.";
        mAction.doDeviceActionAndWait(new DeviceActionMenu());
        UiObject2 menu = mDevice.findObject(mFunUiObjects.getMenuContainerSelector());
        Assert.assertNull(mMessage, menu);
    }

    @Test
    @Category({CategoryFileManagerTests.class})
    public void test42_01OpenPictureFile() {
        final String pattern = "*.png";
        ShellUtils.deleteAllFilesFromDirectory(TEST_PIC_DIR_PATH, pattern);

        mMessage = "Verify open the picture file by image browser.";
        final File testPicFile = mTask.createPicTestFile(mDevice, TEST_PIC_DIR_PATH);
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(testPicFile.getAbsolutePath());

        mAction.doChainedDeviceActionAndWait(new DeviceActionMoveRight())
                .doDeviceActionAndWait(new DeviceActionCenter());
        Assert.assertTrue(mMessage, TestHelper.waitForActivityOpenedByShellCmd(
                TestConstants.FILE_MANAGER_PKG_NAME, ".ui.TVImagePlayerActivity"));

        mMessage = "Verify the operation button on image browser: ";
        final String[] buttonsText = {"上一张", "下一张", "播放", "旋转"};

        UiObject2 container = mDevice.findObject(
                mFunUiObjects.getMenuContainerOfImageBrowserSelector());
        UiObject2 uiBtnTest;
        for (String btnText : buttonsText) {
            mAction.doDeviceActionAndWait(new DeviceActionMoveDown(), 500L);
            uiBtnTest = container.findObject(By.text(btnText));
            Assert.assertNotNull(mMessage + btnText, uiBtnTest);
        }

        mTask.deleteTestFile(testPicFile);
    }

    @Test
    @Category({CategoryFileManagerTests.class})
    public void test42_02RemovePictureFileFromLocalDir() {
        File testPicFile = mTask.createPicTestFile(mDevice, TEST_PIC_DIR_PATH);

        mMessage = "Verify picture file is delete after click Remove menu button.";
        mTask.openLocalFilesCard();
        mTask.navigateToSpecifiedPath(testPicFile.getAbsolutePath());
        mAction.doDeviceActionAndWait(new DeviceActionMoveRight());
        this.removeFileAndConfirm();

        UiObject2 fileDeleted = mDevice.findObject(By.text(testPicFile.getName()));
        Assert.assertNull(mMessage, fileDeleted);

        mTask.deleteTestFile(testPicFile);
    }

    @Test
    @Category({CategoryFileManagerTests.class})
    public void test42_03RemovePictureFileFromPicCategory() {
        File testPicFile = mTask.createPicTestFile(mDevice, TEST_PIC_DIR_PATH);

        mTask.restartFileManagerApp();  // restart app to trigger file scan
        mTask.openCategoryPictureCard();
        mTask.navigateToSpecifiedPath(testPicFile.getParentFile().getName());
        mAction.doDeviceActionAndWait(new DeviceActionMoveRight());

        mMessage = "Verify picture file is delete after click Remove menu button.";
        this.removeFileAndConfirm();
        UiObject2 uiTip = mDevice.findObject(
                mFunUiObjects.getTipsOfEmptyDirFromLocalFilesCardSelector());
        Assert.assertTrue(TestHelper.waitForUiObjectEnabled(uiTip));
        Assert.assertEquals(mMessage, MESSAGE_TEXT_NO_PIC_FOUND, uiTip.getText());

        mTask.deleteTestFile(testPicFile);
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test42_04MessageWhenEmptyForPictureCard() {
        mTask.openCategoryPictureCard();

        mMessage = "Verify the tips when no files in picture card.";
        UiObject2 uiTip = mDevice.findObject(
                mFunUiObjects.getTipsOfEmptyDirFromLocalFilesCardSelector());
        Assert.assertTrue(TestHelper.waitForUiObjectEnabled(uiTip));
        Assert.assertEquals(mMessage, MESSAGE_TEXT_NO_PIC_FOUND, uiTip.getText());

        mMessage = "Verify the menu is NOT shown when no files in picture card.";
        mAction.doDeviceActionAndWait(new DeviceActionMenu());
        UiObject2 menu = mDevice.findObject(mFunUiObjects.getMenuContainerSelector());
        Assert.assertNull(mMessage, menu);
    }

    @Test
    @Ignore
    @Category({CategoryFileManagerTests.class})
    public void test43_01HideMusicFile() {
        // TODO: 2016/11/21 music category
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test43_02MessageWhenEmptyForMusicCard() {
        final String MESSAGE_TEXT_NO_MUSIC_FOUND = "未发现可播放的音乐";

        mMessage = "Verify the tips when no files in music card.";
        mTask.openCategoryMusicCard();
        UiObject2 tips = mDevice.findObject(
                mFunUiObjects.getTipsOfEmptyDirFromLocalFilesCardSelector());
        Assert.assertNotNull(tips);
        Assert.assertEquals(mMessage, MESSAGE_TEXT_NO_MUSIC_FOUND, tips.getText());

        mMessage = "Verify the menu is NOT shown when no files in music card.";
        mAction.doDeviceActionAndWait(new DeviceActionMenu());
        UiObject2 menu = mDevice.findObject(mFunUiObjects.getMenuContainerSelector());
        Assert.assertNull(mMessage, menu);
    }

    @Test
    @Ignore
    @Category({CategoryFileManagerTests.class})
    public void test44_01HideApkFile() {
        // TODO: 2016/11/21 APK category
    }

    @Test
    @Category(CategoryFileManagerTests.class)
    public void test99ClearUpAfterAllTestCasesDone() {
        TaskFileManager.destroyInstance();
        UiObjectsFileManager.destroyInstance();
    }

    private void backToFileManagerHome() {
        mTask.openFileManagerHomePage(true);
    }

    private void removeFileAndConfirm() {
        mTask.showMenuAndClickBtn(TEXT_REMOVE_BUTTON);
        UiObject2 btnConfirm = mDevice.findObject(mFunUiObjects.getYesBtnOfConfirmDialogSelector());
        Assert.assertTrue("Wait for confirm button enabled.",
                TestHelper.waitForUiObjectEnabled(btnConfirm));
        mAction.doClickActionAndWait(btnConfirm);
    }

}
