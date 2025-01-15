package est.wordwise.common.util;

import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.WordBook;
import est.wordwise.common.repository.WordBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordBookService {
    public final WordBookRepository wordBookRepository;

    public List<WordBook> findWordBookByMember(Member member) {
        return  wordBookRepository.findByMember(member);
    }

}
