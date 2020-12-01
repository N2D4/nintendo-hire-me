import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HireMe {

    private static int[] b2i(byte... bytes) {
        int[] arr = new int[bytes.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = bytes[i] & 255;
        }
        return arr;
    }

    private static int[] l2i(long... longs) {
        int[] arr = new int[longs.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) longs[i];
        }
        return arr;
    }

    private static byte[] i2b(int... ints) {
        byte[] b = new byte[ints.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) ints[i];
        }
        return b;
    }

    public static byte[] sequence = i2b(
        0x66,0xd5,0x4e,0x28,0x5f,0xff,0x6b,0x53,0xac,0x3b,0x34,0x14,0xb5,0x3c,0xb2,0xc6,
        0xa4,0x85,0x1e,0x0d,0x86,0xc7,0x4f,0xba,0x75,0x5e,0xcb,0xc3,0x6e,0x48,0x79,0x8f
    );

    public static final int[] conf = { 0xac, 0xd1, 0x25, 0x94, 0x1f, 0xb3, 0x33, 0x28, 0x7c, 0x2b, 0x17, 0xbc, 0xf6, 0xb0, 0x55, 0x5d,
            0x8f, 0xd2, 0x48, 0xd4, 0xd3, 0x78, 0x62, 0x1a, 0x02, 0xf2, 0x01, 0xc9, 0xaa, 0xf0, 0x83, 0x71, 0x72, 0x4b,
            0x6a, 0xe8, 0xe9, 0x42, 0xc0, 0x53, 0x63, 0x66, 0x13, 0x4a, 0xc1, 0x85, 0xcf, 0x0c, 0x24, 0x76, 0xa5, 0x6e,
            0xd7, 0xa1, 0xec, 0xc6, 0x04, 0xc2, 0xa2, 0x5c, 0x81, 0x92, 0x6c, 0xda, 0xc6, 0x86, 0xba, 0x4d, 0x39, 0xa0,
            0x0e, 0x8c, 0x8a, 0xd0, 0xfe, 0x59, 0x96, 0x49, 0xe6, 0xea, 0x69, 0x30, 0x52, 0x1c, 0xe0, 0xb2, 0x05, 0x9b,
            0x10, 0x03, 0xa8, 0x64, 0x51, 0x97, 0x02, 0x09, 0x8e, 0xad, 0xf7, 0x36, 0x47, 0xab, 0xce, 0x7f, 0x56, 0xca,
            0x00, 0xe3, 0xed, 0xf1, 0x38, 0xd8, 0x26, 0x1c, 0xdc, 0x35, 0x91, 0x43, 0x2c, 0x74, 0xb4, 0x61, 0x9d, 0x5e,
            0xe9, 0x4c, 0xbf, 0x77, 0x16, 0x1e, 0x21, 0x1d, 0x2d, 0xa9, 0x95, 0xb8, 0xc3, 0x8d, 0xf8, 0xdb, 0x34, 0xe1,
            0x84, 0xd6, 0x0b, 0x23, 0x4e, 0xff, 0x3c, 0x54, 0xa7, 0x78, 0xa4, 0x89, 0x33, 0x6d, 0xfb, 0x79, 0x27, 0xc4,
            0xf9, 0x40, 0x41, 0xdf, 0xc5, 0x82, 0x93, 0xdd, 0xa6, 0xef, 0xcd, 0x8d, 0xa3, 0xae, 0x7a, 0xb6, 0x2f, 0xfd,
            0xbd, 0xe5, 0x98, 0x66, 0xf3, 0x4f, 0x57, 0x88, 0x90, 0x9c, 0x0a, 0x50, 0xe7, 0x15, 0x7b, 0x58, 0xbc, 0x07,
            0x68, 0x3a, 0x5f, 0xee, 0x32, 0x9f, 0xeb, 0xcc, 0x18, 0x8b, 0xe2, 0x57, 0xb7, 0x49, 0x37, 0xde, 0xf5, 0x99,
            0x67, 0x5b, 0x3b, 0xbb, 0x3d, 0xb5, 0x2d, 0x19, 0x2e, 0x0d, 0x93, 0xfc, 0x7e, 0x06, 0x08, 0xbe, 0x3f, 0xd9,
            0x2a, 0x70, 0x9a, 0xc8, 0x7d, 0xd8, 0x46, 0x65, 0x22, 0xf4, 0xb9, 0xa2, 0x6f, 0x12, 0x1b, 0x14, 0x45, 0xc7,
            0x87, 0x31, 0x60, 0x29, 0xf7, 0x73, 0x2c, 0x97, 0x72, 0xcd, 0x89, 0xa6, 0x88, 0x4c, 0xe8, 0x83, 0xeb, 0x59,
            0xca, 0x50, 0x3f, 0x27, 0x4e, 0xae, 0x43, 0xd5, 0x6e, 0xd0, 0x99, 0x7b, 0x7c, 0x40, 0x0c, 0x52, 0x86, 0xc1,
            0x46, 0x12, 0x5a, 0x28, 0xa8, 0xbb, 0xcb, 0xf0, 0x11, 0x95, 0x26, 0x0d, 0x34, 0x66, 0x22, 0x18, 0x6f, 0x51,
            0x9b, 0x3b, 0xda, 0xec, 0x5e, 0x00, 0x2a, 0xf5, 0x8f, 0x61, 0xba, 0x96, 0xb3, 0xd1, 0x30, 0xdc, 0x33, 0x75,
            0xe9, 0x6d, 0xc8, 0xa1, 0x3a, 0x3e, 0x5f, 0x9d, 0xfd, 0xa9, 0x31, 0x9f, 0xaa, 0x85, 0x2f, 0x92, 0xaf, 0x67,
            0x78, 0xa5, 0xab, 0x03, 0x21, 0x4f, 0xb9, 0xad, 0xfe, 0xf3, 0x42, 0xfc, 0x17, 0xd7, 0xee, 0xa3, 0xd8, 0x80,
            0x14, 0x2e, 0xa0, 0x47, 0x55, 0xc4, 0xff, 0xe5, 0x13, 0x3f, 0x81, 0xb6, 0x7a, 0x94, 0xd0, 0xb5, 0x54, 0xbf,
            0x91, 0xa7, 0x37, 0xf1, 0x6b, 0xc9, 0x1b, 0xb1, 0x3c, 0xb6, 0xd9, 0x32, 0x24, 0x8d, 0xf2, 0x82, 0xb4, 0xf9,
            0xdb, 0x7d, 0x44, 0xfb, 0x1e, 0xd4, 0xea, 0x5d, 0x35, 0x69, 0x23, 0x71, 0x57, 0x01, 0x06, 0xe4, 0x55, 0x9a,
            0xa4, 0x58, 0x56, 0xc7, 0x4a, 0x8c, 0x8a, 0xd6, 0x6a, 0x49, 0x70, 0xc5, 0x8e, 0x0a, 0x62, 0xdc, 0x29, 0x4b,
            0x42, 0x41, 0xcb, 0x2b, 0xb7, 0xce, 0x08, 0xa1, 0x76, 0x1d, 0x1a, 0xb8, 0xe3, 0xcc, 0x7e, 0x48, 0x20, 0xe6,
            0xf8, 0x45, 0x93, 0xde, 0xc3, 0x63, 0x0f, 0xb0, 0xac, 0x5c, 0xba, 0xdf, 0x07, 0x77, 0xe7, 0x4e, 0x1f, 0x28,
            0x10, 0x6c, 0x59, 0xd3, 0xdd, 0x2d, 0x65, 0x39, 0xb2, 0x74, 0x84, 0x3d, 0xf4, 0xbd, 0xc7, 0x79, 0x60, 0x0b,
            0x4d, 0x33, 0x36, 0x25, 0xbc, 0xe0, 0x09, 0xcf, 0x5b, 0xe2, 0x38, 0x9e, 0xc0, 0xef, 0xd2, 0x16, 0x05, 0xbe,
            0x53, 0xf7, 0xc2, 0xc6, 0xa2, 0x24, 0x98, 0x1c, 0xad, 0x04 };

    public static final int[][][] iconf = inverseConf();
    private static int[][][] inverseConf() {
        int[][][] res = new int[256][2][0];
        for (int i = 0; i < conf.length; i++) {
            res[conf[i]][i >= 256 ? 1 : 0] = arrAdd(res[conf[i]][i >= 256 ? 1 : 0], i);
        }
        return res;
    }

    public static final int[] diff = { 0xf26cb481, 0x16a5dc92, 0x3c5ba924, 0x79b65248, 0x2fc64b18, 0x615acd29, 0xc3b59a42, 0x976b2584,
            0x6cf281b4, 0xa51692dc, 0x5b3c24a9, 0xb6794852, 0xc62f184b, 0x5a6129cd, 0xb5c3429a, 0x6b978425, 0xb481f26c,
            0xdc9216a5, 0xa9243c5b, 0x524879b6, 0x4b182fc6, 0xcd29615a, 0x9a42c3b5, 0x2584976b, 0x81b46cf2, 0x92dca516,
            0x24a95b3c, 0x4852b679, 0x184bc62f, 0x29cd5a61, 0x429ab5c3, 0x84256b97 };

    public static byte[] forwardOriginal(byte[] input) {
        byte[] in = Arrays.copyOf(input, input.length);
        byte[] out = new byte[32];
        for (int i = 0; i < 256; i++) {
            for (byte j = 0; j < 32; j++) {
                out[j] = i2b(conf)[in[j] & 255];
                in[j] = 0;
            }

            for (byte j = 0; j < 32; j++) {
                for (byte k = 0; k < 32; k++) {
                    in[j] ^= out[k] * ((diff[j] >> k) & 1);
                }
            }
        }

        for (byte i = 0; i < 16; i++) {
            out[i] = (byte) (conf[in[i * 2] & 255] ^ conf[(in[i * 2 + 1] & 255) + 256]);
        }

        return Arrays.copyOf(out, 16);
    }

    public static int[] beastep1(int[] input) {
        int[] out = new int[32];
        for (int j = 0; j < 32; j++) {
            out[j] = conf[input[j]];
        } 
        return out;
    }
    
    public static Stream<int[]> beastep1Back(int[] target) {
        Stream<int[]> res = Stream.of(new int[0]);
        for (int j = 0; j < 32; j++) {
            final int fj = j;
            res = res.flatMap(a -> Arrays.stream(iconf[target[fj]][0]).mapToObj(b -> arrAdd(a, b)));
        } 
        return res;
    }

    public static int[] beastep2(int[] input) {
        return mmul(input, diff);
    }
    
    public static Stream<int[]> beastep2Back(int[] target) {
        return Stream.of(msolve(diff, target));
    }

    public static int[] beastep(int[] input) {
        return beastep2(beastep1(input));
    }
    
    public static Stream<int[]> beastepBack(int[] target) {
        return beastep2Back(target).flatMap(HireMe::beastep1Back);
    }

    public static byte[] beast(byte[] input) {
        int[] in = b2i(input);
        for (int i = 0; i < 256; i++) {
            in = beastep(in);
        }
        return i2b(in);
    }
    
    public static Stream<byte[]> beastBack(byte[] target) {
        Stream<int[]> res = Stream.of(b2i(target));
        for (int i = 0; i < 256; i++) {
            res = res.flatMap(HireMe::beastepBack);
        }
        return res.map(HireMe::i2b);
    }

    public static byte[] beauty(byte[] input) {
        int[] in = b2i(input);
        int[] out = new int[input.length / 2];
        for (int i = 0; i < out.length; i++) {
            out[i] = conf[in[2*i]] ^ conf[in[2*i + 1] + 256];
        }
        return i2b(out);
    }
    
    public static Stream<byte[]> beautyBack(byte[] target) {
        int[] out = b2i(target);
        Stream<int[]> res = Stream.of(new int[0]);
        for (int i = 0; i < target.length; i++) {
            int fi = i;
            res = res.flatMap(a -> IntStream.range(0, 256)
                                            .boxed()
                                            .flatMap(j -> Arrays.stream(iconf[conf[j] ^ out[fi]][1])
                                                                .mapToObj(p -> arrAdd(a, j, p - 256))
                                            )
            );
        }
        return res.map(HireMe::i2b);
    }
    
    public static byte[] forward(byte[] input) {
        return beauty(beast(input));
    }

    public static Stream<byte[]> backward(byte[] target) {
        while (true) {
            return beautyBack(target).flatMap(HireMe::beastBack);
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] input = sequence;
        byte[] target = "Hire me!!!!!!!!\0".getBytes(StandardCharsets.US_ASCII);
    
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        // System.out.println(Arrays.toString(beauty(new byte[] { 'a', 'b' })));
        // System.out.println(Arrays.toString(beautyBack(beauty(new byte[] { 'a', 'b' }))));

        try {
            byte[] bwdInput = backward(target).findAny().orElse(new byte[32]);
            System.out.println("Input: " + renderByteArr(input));
            System.out.println("Backward: " + renderByteArr(bwdInput));
            System.out.println("Backward forward: " + renderByteArr(forward(bwdInput)));
            System.out.println("Target: " + renderByteArr(target));
            System.out.println();

            System.out.println("Original forward: " + renderByteArr(forwardOriginal(input)));
            System.out.println("Forward: " + renderByteArr(forward(input)));
            System.out.println();
        } catch (Exception e) {
            System.err.println("Exception occured!");
            e.printStackTrace();
        }

        renderTestI("Beastep1", HireMe::beastep1, HireMe::beastep1Back);
        renderTestI("Beastep2", HireMe::beastep2, HireMe::beastep2Back);
        renderTestI("Beastep", HireMe::beastep, HireMe::beastepBack);
        renderTestB("Beast", HireMe::beast, HireMe::beastBack);
        renderTestB("Beauty", HireMe::beauty, HireMe::beautyBack);
        renderTestB("Forward", HireMe::forward, HireMe::backward);
        System.out.println();
        System.out.println();
    }


    private static String renderByteArr(byte[] arr) throws Exception {
        if (arr == null) {
            return "null";
        }
        CharsetDecoder decoder = StandardCharsets.US_ASCII.newDecoder();
        decoder.onMalformedInput(CodingErrorAction.IGNORE)
               .onUnmappableCharacter(CodingErrorAction.REPLACE)
               .replaceWith("�");
        return bytesToHex(arr) + ": " + decoder.decode(ByteBuffer.wrap(arr)).toString();
    }

    private static void renderTestB(String name, Function<byte[], byte[]> forward, Function<byte[], Stream<byte[]>> backward) {
        System.out.print(name + " test: ");
        try {
            byte[] expected = forward.apply(sequence);
            for (byte[] arr : backward.apply(expected).limit(1000).toArray(byte[][]::new)) {
                byte[] fwa = forward.apply(arr);
                if (!Arrays.equals(expected, fwa)) {
                    System.out.println("FAILED with wrong output! expected:\n    " + renderByteArr(expected) + "\n  actual:\n    " + renderByteArr(fwa));
                    return;
                }
            }
            System.out.println("Success!");
        } catch (Exception e) {
            System.out.println("FAILED with exception! " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    private static void renderTestI(String name, Function<int[], int[]> forward, Function<int[], Stream<int[]>> backward) {
        System.out.print(name + " test: ");
        try {
            int[] expected = forward.apply(b2i(sequence));
            for (int[] arr : backward.apply(expected).limit(1000).toArray(int[][]::new)) {
                int[] fwa = forward.apply(arr);
                if (!Arrays.equals(expected, fwa)) {
                    System.out.println("FAILED with wrong output! expected:\n    " + renderByteArr(i2b(expected)) + "\n  actual:\n    " + renderByteArr(i2b(fwa)));
                    return;
                }
            }
            System.out.println("Success!");
        } catch (Exception e) {
            System.out.println("FAILED with exception! " + e.getClass().getName() + " - " + e.getMessage());
            return;
        }
    }

    // Thanks maybeWeCouldStealAVan and Jemenake for this snippet
    // https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }


    private static int[] mmul(int[] a, int[] matrix) {
        int[] res = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < a.length; j++) {
                res[i] ^= a[j] * ((matrix[i] >> j) & 1);
            }
        }
        return res;
    }

    private static int[] msolve(int[] squareMatrix, int[] b) {
        long[] matrix = Arrays.stream(squareMatrix).mapToLong(a -> a & (1l << 32) - 1).toArray();
        int[] a = Arrays.copyOf(b, b.length);
        int[] p = IntStream.range(0, matrix.length).toArray();

        // triangular matrix
        for (int i = 0; i < matrix.length; i++) {
            // find pivot
            int pivotIndex = -1;
            for (int j = i; j < matrix.length; j++) {
                if ((matrix[p[j]] >>> (matrix.length - i - 1) & 1) == 1) {
                    pivotIndex = j;
                    break;
                }
            }
            if (pivotIndex < 0) {
                throw new RuntimeException("Matrix not diagonalizable!");
            }
            swap(p, i, pivotIndex);


            for (int j = i + 1; j < matrix.length; j++) {
                if ((matrix[p[j]] >>> (matrix.length - i - 1) & 1) == 1) {
                    matrix[p[j]] ^= matrix[p[i]];
                    a[p[j]] ^= a[p[i]];
                }
            }
        }

        // diagonalize
        for (int i = a.length - 1; i >= 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if ((matrix[p[j]] >>> (matrix.length - i - 1) & 1) == 1) {
                    matrix[p[j]] ^= matrix[p[i]];
                    a[p[j]] ^= a[p[i]];
                }
            }
        }

        return mmul(a, l2i(mtr(matrix)));
    }

    private static long[] mtr(long[] matrix) {
        long[] t = new long[matrix.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                t[j] |= (matrix[i] >>> (t.length - j - 1) & 1) << (t.length - i - 1);
            }
        }
        return t;
    }

    private static void swap(int[] arr, int i, int j) {
        int el = arr[i];
        arr[i] = arr[j];
        arr[j] = el;
    }

    private static int[] arrAdd(int[] arr, int... el) {
        int[] res = Arrays.copyOf(arr, arr.length + el.length);
        System.arraycopy(el, 0, res, arr.length, el.length);
        return res;
    }

}
