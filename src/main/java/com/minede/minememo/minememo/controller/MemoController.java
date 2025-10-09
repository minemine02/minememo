package com.minede.minememo.minememo.controller;

import com.minede.minememo.minememo.model.Memo;
import com.minede.minememo.minememo.service.MemoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/memos")
public class MemoController {

    // 使うサービスを保持
    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    // POST /api/memos (新規制作)
    @PostMapping
    public ResponseEntity<Memo> create(@RequestBody @Valid Memo memo) {
        Memo saved = memoService.saveMemo(memo);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // DELETE /api/memos/{id} (削除)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memoService.deleteMemo(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/memos/{id} (1件取得)
    @GetMapping("/{id}")
    public ResponseEntity<Memo> getOne(@PathVariable Long id) {
        Memo found = memoService.findByMemoId(id);
        return ResponseEntity.ok(found);
    }

    // GET /api/memos (一覧取得)
    @GetMapping
    public ResponseEntity<List<Memo>> getAll() {
        return ResponseEntity.ok(memoService.findAll());
    }

    // PUT /api/memos/{id} (更新)
    @PutMapping("/{id}")
    public ResponseEntity<Memo> update(@PathVariable Long id, @RequestBody @Valid Memo memo) {
        Memo updated = memoService.updateMemo(id, memo);
        return ResponseEntity.ok(updated);
    }
}
