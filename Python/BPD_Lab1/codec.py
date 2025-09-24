from bitarray import bitarray

class HammingCodec:

    @staticmethod
    def ascii_to_bitarray(text: str) -> bitarray:
        ba = bitarray()
        ba.frombytes(text.encode('ascii'))
        ba.reverse()
        return ba
    
    @staticmethod
    def bitarray_to_ascii(ba: bitarray) -> str:
        ba.reverse()
        return ba.tobytes().decode('ascii')


    @staticmethod
    def encode(text: str) -> bitarray:
        ba = HammingCodec.ascii_to_bitarray(text)
        HammingCodec._insert_parity_bits(ba)
        for i, parity in HammingCodec._calculate_parity_bit_values(ba).items():
            ba[i] = parity
        return ba

    @staticmethod
    def decode(ba: bitarray) -> str:
        errors = HammingCodec._check_parity_bits(ba)
        if errors:
            print(f"[!] Спроба виправлення помилки у позиції {sum(errors)}")
            ba[sum(errors)-1] ^= 1
        for i in range(len(ba)-1, -1, -1):
            if HammingCodec._is_pow2(i):
                del ba[i]
        return HammingCodec.bitarray_to_ascii(ba)
    

    @staticmethod
    def _is_pow2(n: int) -> bool:
        return (n + 1) & n == 0

    @staticmethod
    def _has_1_at(n: int, pos: int) -> bool:
        return (n + 1) & (pos + 1) != 0


    @staticmethod
    def _insert_parity_bits(ba: bitarray) -> None:
        for i in range(len(ba)):
            if HammingCodec._is_pow2(i):
                ba.insert(i, 0)

    @staticmethod
    def _calculate_parity_bit_values(ba: bitarray) -> dict[int, int]:
        values = {}
        for p in range(len(ba)):
            if not HammingCodec._is_pow2(p): continue
            parity = 0
            for d in range(p + 1, len(ba)):
                if HammingCodec._is_pow2(d): continue
                if HammingCodec._has_1_at(d, p):
                    parity ^= ba[d]
            values[p] = parity
        return values

    @staticmethod
    def _check_parity_bits(ba: bitarray) -> int:
        errors = []
        for i, parity in HammingCodec._calculate_parity_bit_values(ba).items():
            if ba[i] != parity:
                errors.append(i + 1)
        return errors
