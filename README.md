Entity 생성은 from 메서드를 통해 생성합니다.
   public static Example from(ExampleCreateDto exampleCreateDto) {
        Example example = new Example();
        example.word = exampleCreateDto.getWord();
        example.sentence = exampleCreateDto.getSentence();
        example.sentenceMeaning = exampleCreateDto.getSentenceMeaning;
        return example;
    }
각자 pull을 당기고 branch를 기능단위 feature로 생성하여 작업을 시작하시면 됩니다.
현재 올라와 있는 from메서드에 인자로 임시로 정의되어 있는 Dto들은 생성해서 각자 정의해서 사용하도록 합니다.

경천님의 Entity중 리프레시 토큰과, 블랙리스트 테이블은 정의하지 않았습니다. 경천님이 판단하셔서 Entity생성 하셔서 필드 정의하고 사용하시면 됩니다.
