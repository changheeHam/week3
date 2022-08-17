package com.sparta.board.controller;

import com.sparta.board.domain.Memo;
import com.sparta.board.dto.MemoRequestDto;
import com.sparta.board.repository.MemoRepository;
import com.sparta.board.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class MemoController {

    private final MemoRepository memoRepository;
    private final MemoService memoService;

    @PostMapping("/api/memos")
    public String createMemo(@RequestBody MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);
        memoRepository.save(memo);
        return "완료";
    }

    @GetMapping("/api/memos")
    public List<Memo> getMemos() {
        return memoRepository.findAllByOrderByModifiedAtDesc();
    }

    @DeleteMapping("/api/memos/{id}")
    public Long deleteMemo(@PathVariable Long id,@RequestBody MemoRequestDto requestDto) {
        Optional<Memo> memos = memoRepository.findById(id);
        if (memos.isPresent()){
            System.out.println("post.get().getPassword() : " + memos.get().getPassword() + " requestDto.getPassword() : " + requestDto.getPassword());
            if (memos.get().getPassword().equals(requestDto.getPassword())) {
                memoRepository.deleteById(id);
            } else {
                System.out.println("비밀번호 오류");
                return 0L;
            }
        }
        return id;
    }

    @PutMapping("/api/memos/{id}")
    public Long updateMemo(@PathVariable long id, @RequestBody MemoRequestDto requestDto){
        Optional<Memo> memos = memoRepository.findById(id);
        if (memos.isPresent()){
            System.out.println("post.get().getPassword() : " + memos.get().getPassword() + " requestDto.getPassword() : " + requestDto.getPassword());
            if (memos.get().getPassword().equals(requestDto.getPassword())) {
                memoService.update(id, requestDto);
            } else {
                System.out.println("비밀번호 오류");
                return 0L;
            }
        }
        return id;
    }


}