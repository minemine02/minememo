package com.minede.minememo.minememo.service;

import com.minede.minememo.minememo.model.Memo;
import com.minede.minememo.minememo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.time.LocalDateTime;

// 保存、削除
@Service
public class MemoService {
    // Private Data Field(s)
    private static final Logger logger = LoggerFactory.getLogger(MemoService.class);
    // Repository
    private final MemoRepository memoRepository;
    public MemoService (MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    /**
     * 新しいMEMOを保存します
     * <p>
     *　タイトルまたはタイトルがNULLの場合throwします。
     *
     * @param memo 保存対象のMEMOオブジェクト
     * @return　保存されたMEMOオブジェクト (自動採番されたIDを含む)
     * @throws IllegalArgumentException タイトルまたはないようはNULLの場合
     */
    // 保存
    @Transactional
    public Memo saveMemo (Memo memo) {
        // Null Catcher
        if (memo.getTitle() == null || memo.getContent() == null) {
            throw new IllegalArgumentException("Title and content must not be null.");
        }

        //日付値を作成
        if (memo.getCreatedAt() == null) {
            memo.setCreatedAt(LocalDateTime.now());
        }
        //年・月・日の受取
        memo.setYear(memo.getCreatedAt().getYear());
        memo.setMonth(memo.getCreatedAt().getMonthValue());
        memo.setDay(memo.getCreatedAt().getDayOfMonth());

        // Memoの保存
        logger.info("MEMO ID: {} の保存を行います。", memo.getId());
        Memo savedMemo = memoRepository.save(memo);
        // Debug
        logger.debug("Saved memo: {}", savedMemo);
        logger.info("MEMO ID: {} の保存が完了しました。", savedMemo.getId());
        return savedMemo;
    }

    /**
     * 指定されたIDのMEMOを削除します。
     * <p>
     * IDが存在しない場合はthrowします。
     *
     * @param id 削除対象のMEMO ID
     * @throws IllegalArgumentException 指定されたIDのMEMOが存在しない場合
     */
    // 削除
    @Transactional
    public void deleteMemo (Long id) {
        // IDの存在チェック
        if (!memoRepository.existsById(id)) {
            throw new IllegalArgumentException("指定されたMEMO IDが存在しません: " + id);
        }
        // 削除
        logger.info("MEMO ID: {} の削除を行います。", id);
        memoRepository.deleteById(id);
        // Debug
        logger.debug("Deleted memo: {}", id);
        logger.info("MEMO ID: {} の削除を完了しました。", id);
    }

    /**
     * 指定されたIDのMEMOを取得します。
     * <p>
     * IDが存在しない場合はthrowします。
     *
     * @param id memoRepositoryから取得されたMEMOのID
     * @return IllegalArgumentException IDが見つかれない場合
     */
    // 読み込み
    public Memo findByMemoId (Long id) {
        logger.info("MEMO ID: {} の検索を行います。", id);
        //MEMO ID1は存在してるかチェック
        return memoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("指定されたMEMO IDが存在しません: " + id)));
    }

    /**
     * すべてのMEMOをDBから取得します。
     * <p>
     * MEMOが存在しない場合は空のリストを返します。
     *
     * @return memoRepositoryに入っている全部のMEMO
     */
    // 全読み込み
    public List<Memo> findAll() {
        logger.info("全MEMOの検索を行います。");
        // 全部のMEMOを取得しよう
        List<Memo> memos = memoRepository.findAll();

        // MEMOがあるかどうか
        if (memos.isEmpty()) {
            logger.info("MEMOはありません。");
        } else {
            logger.info("{}件のMEMOを取得しました。", memos.size());
        }

        return memos;
    }

    /**
     * 指定されたIDのメモを更新します。
     * <p>
     * 存在しないIDが指定された場合は例外をスローします。
     * タイトルまたは内容がnullの場合は、その項目は上書きされません。
     *
     * @param id　更新対象のメモID
     * @param memoData 新しいタイトルまたは内容を含むMemoオブジェクト
     * @return 更新後のMemoオブジェクト
     * @throws IllegalArgumentException 指定されたIDのメモが存在しない場合
     */
    // 更新
    @Transactional
    public Memo updateMemo (Long id, Memo memoData) {
        logger.info("MEMO ID: {} の更新を行います。", id);
        Memo existing = memoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("指定されたMEMO IDが存在しません: " + id));

        // Null checker + 更新対象フィールドだけ上書き
        if (memoData.getTitle() != null) {
            existing.setTitle(memoData.getTitle());
        }
        if (memoData.getContent() != null) {
            existing.setContent(memoData.getContent());
        }
        Memo updated = memoRepository.save(existing);
        logger.info("MEMO ID: {} の更新は完了しました。", id);
        logger.debug("Updated memo: {}", updated);
        logger.debug("更新内容・Title: {}, Content: {}", memoData.getTitle(), memoData.getContent());

        return updated;
    }
}

/*
MAPの形・参考

// Immutable memo fields (変更できないメモ内容)
        Map<String, Object> immutableMemoFields = Map.of(
                "Id", memo.getId(),
                "CreatedAt", memo.getCreatedAt(),
                "Year", memo.getYear(),
                "Month", memo.getMonth(),
                "Day", memo.getDay()
        );
        // Mutable memo fields (変更可能メモ内容)
        Map<String, Object> mutableMemoFields = new HashMap<>();
        mutableMemoFields.put("Title", memo.getTitle());
        mutableMemoFields.put("Content", memo.getContent());

        // Full memo map
        Map<String, Object> fullMemo = new HashMap<>(immutableMemoFields);
        fullMemo.putAll(mutableMemoFields);
 */
