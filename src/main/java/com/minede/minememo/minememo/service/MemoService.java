package com.minede.minememo.minememo.service;

import com.minede.minememo.minememo.model.Memo;
import com.minede.minememo.minememo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    // 保存
    @Transactional
    public Memo saveMemo (Memo memo) {
        // Null Catcher
        if (memo.getTitle() == null || memo.getContent() == null) {
            throw new IllegalArgumentException("Title and content must not be null.");
        }

        // Memoの保存
        logger.info("MEMO ID: {} の保存を行います。", memo.getId());
        Memo savedMemo = memoRepository.save(memo);
        // Debug
        logger.debug("Saved memo: {}", savedMemo);
        logger.info("MEMO ID: {} の保存が完了しまいた。", savedMemo.getId());
        return savedMemo;
    }

    // 読み込み
    public Memo findMemoById (Long id) {
        return memoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(("指定されたMEMO IDが存在しません: " + id)));
    }

    //削除
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
