var CryptoJS = CryptoJS || function (a, b) {
        var c = {}, d = c.lib = {}, e = d.Base = function () {
            function a() {
            }

            return {
                extend: function (b) {
                    a.prototype = this;
                    var c = new a;
                    return b && c.mixIn(b), c.$super = this, c
                }, create: function () {
                    var a = this.extend();
                    return a.init.apply(a, arguments), a
                }, init: function () {
                }, mixIn: function (a) {
                    for (var b in a)a.hasOwnProperty(b) && (this[b] = a[b]);
                    a.hasOwnProperty("toString") && (this.toString = a.toString)
                }, clone: function () {
                    return this.$super.extend(this)
                }
            }
        }(), f = d.WordArray = e.extend({
            init: function (a, c) {
                a = this.words = a || [], this.sigBytes = c != b ? c : 4 * a.length
            }, toString: function (a) {
                return (a || h).stringify(this)
            }, concat: function (a) {
                var b = this.words, c = a.words, d = this.sigBytes, a = a.sigBytes;
                if (this.clamp(), d % 4)for (var e = 0; a > e; e++)b[d + e >>> 2] |= (c[e >>> 2] >>> 24 - 8 * (e % 4) & 255) << 24 - 8 * ((d + e) % 4); else if (65535 < c.length)for (e = 0; a > e; e += 4)b[d + e >>> 2] = c[e >>> 2]; else b.push.apply(b, c);
                return this.sigBytes += a, this
            }, clamp: function () {
                var b = this.words, c = this.sigBytes;
                b[c >>> 2] &= 4294967295 << 32 - 8 * (c % 4), b.length = a.ceil(c / 4)
            }, clone: function () {
                var a = e.clone.call(this);
                return a.words = this.words.slice(0), a
            }, random: function (b) {
                for (var c = [], d = 0; b > d; d += 4)c.push(4294967296 * a.random() | 0);
                return f.create(c, b)
            }
        }), g = c.enc = {}, h = g.Hex = {
            stringify: function (a) {
                for (var b = a.words, a = a.sigBytes, c = [], d = 0; a > d; d++) {
                    var e = b[d >>> 2] >>> 24 - 8 * (d % 4) & 255;
                    c.push((e >>> 4).toString(16)), c.push((15 & e).toString(16))
                }
                return c.join("")
            }, parse: function (a) {
                for (var b = a.length, c = [], d = 0; b > d; d += 2)c[d >>> 3] |= parseInt(a.substr(d, 2), 16) << 24 - 4 * (d % 8);
                return f.create(c, b / 2)
            }
        }, i = g.Latin1 = {
            stringify: function (a) {
                for (var b = a.words, a = a.sigBytes, c = [], d = 0; a > d; d++)c.push(String.fromCharCode(b[d >>> 2] >>> 24 - 8 * (d % 4) & 255));
                return c.join("")
            }, parse: function (a) {
                for (var b = a.length, c = [], d = 0; b > d; d++)c[d >>> 2] |= (255 & a.charCodeAt(d)) << 24 - 8 * (d % 4);
                return f.create(c, b)
            }
        }, j = g.Utf8 = {
            stringify: function (a) {
                try {
                    return decodeURIComponent(escape(i.stringify(a)))
                } catch (b) {
                    throw Error("Malformed UTF-8 data")
                }
            }, parse: function (a) {
                return i.parse(unescape(encodeURIComponent(a)))
            }
        }, k = d.BufferedBlockAlgorithm = e.extend({
            reset: function () {
                this._data = f.create(), this._nDataBytes = 0
            }, _append: function (a) {
                "string" == typeof a && (a = j.parse(a)), this._data.concat(a), this._nDataBytes += a.sigBytes
            }, _process: function (b) {
                var c = this._data, d = c.words, e = c.sigBytes, g = this.blockSize, h = e / (4 * g), h = b ? a.ceil(h) : a.max((0 | h) - this._minBufferSize, 0), b = h * g, e = a.min(4 * b, e);
                if (b) {
                    for (var i = 0; b > i; i += g)this._doProcessBlock(d, i);
                    i = d.splice(0, b), c.sigBytes -= e
                }
                return f.create(i, e)
            }, clone: function () {
                var a = e.clone.call(this);
                return a._data = this._data.clone(), a
            }, _minBufferSize: 0
        });
        d.Hasher = k.extend({
            init: function () {
                this.reset()
            }, reset: function () {
                k.reset.call(this), this._doReset()
            }, update: function (a) {
                return this._append(a), this._process(), this
            }, finalize: function (a) {
                return a && this._append(a), this._doFinalize(), this._hash
            }, clone: function () {
                var a = k.clone.call(this);
                return a._hash = this._hash.clone(), a
            }, blockSize: 16, _createHelper: function (a) {
                return function (b, c) {
                    return a.create(c).finalize(b)
                }
            }, _createHmacHelper: function (a) {
                return function (b, c) {
                    return l.HMAC.create(a, c).finalize(b)
                }
            }
        });
        var l = c.algo = {};
        return c
    }(Math);
!function () {
    var a = CryptoJS, b = a.lib.WordArray;
    a.enc.Base64 = {
        stringify: function (a) {
            var b = a.words, c = a.sigBytes, d = this._map;
            a.clamp();
            for (var a = [], e = 0; c > e; e += 3)for (var f = (b[e >>> 2] >>> 24 - 8 * (e % 4) & 255) << 16 | (b[e + 1 >>> 2] >>> 24 - 8 * ((e + 1) % 4) & 255) << 8 | b[e + 2 >>> 2] >>> 24 - 8 * ((e + 2) % 4) & 255, g = 0; 4 > g && c > e + .75 * g; g++)a.push(d.charAt(f >>> 6 * (3 - g) & 63));
            if (b = d.charAt(64))for (; a.length % 4;)a.push(b);
            return a.join("")
        }, parse: function (a) {
            var a = a.replace(/\s/g, ""), c = a.length, d = this._map, e = d.charAt(64);
            e && (e = a.indexOf(e), -1 != e && (c = e));
            for (var e = [], f = 0, g = 0; c > g; g++)if (g % 4) {
                var h = d.indexOf(a.charAt(g - 1)) << 2 * (g % 4), i = d.indexOf(a.charAt(g)) >>> 6 - 2 * (g % 4);
                e[f >>> 2] |= (h | i) << 24 - 8 * (f % 4), f++
            }
            return b.create(e, f)
        }, _map: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
    }
}(), function (a) {
    function b(a, b, c, d, e, f, g) {
        return a = a + (b & c | ~b & d) + e + g, (a << f | a >>> 32 - f) + b
    }

    function c(a, b, c, d, e, f, g) {
        return a = a + (b & d | c & ~d) + e + g, (a << f | a >>> 32 - f) + b
    }

    function d(a, b, c, d, e, f, g) {
        return a = a + (b ^ c ^ d) + e + g, (a << f | a >>> 32 - f) + b
    }

    function e(a, b, c, d, e, f, g) {
        return a = a + (c ^ (b | ~d)) + e + g, (a << f | a >>> 32 - f) + b
    }

    var f = CryptoJS, g = f.lib, h = g.WordArray, g = g.Hasher, i = f.algo, j = [];
    !function () {
        for (var b = 0; 64 > b; b++)j[b] = 4294967296 * a.abs(a.sin(b + 1)) | 0
    }(), i = i.MD5 = g.extend({
        _doReset: function () {
            this._hash = h.create([1732584193, 4023233417, 2562383102, 271733878])
        }, _doProcessBlock: function (a, f) {
            for (var g = 0; 16 > g; g++) {
                var h = f + g, i = a[h];
                a[h] = 16711935 & (i << 8 | i >>> 24) | 4278255360 & (i << 24 | i >>> 8)
            }
            for (var h = this._hash.words, i = h[0], k = h[1], l = h[2], m = h[3], g = 0; 64 > g; g += 4)16 > g ? (i = b(i, k, l, m, a[f + g], 7, j[g]), m = b(m, i, k, l, a[f + g + 1], 12, j[g + 1]), l = b(l, m, i, k, a[f + g + 2], 17, j[g + 2]), k = b(k, l, m, i, a[f + g + 3], 22, j[g + 3])) : 32 > g ? (i = c(i, k, l, m, a[f + (g + 1) % 16], 5, j[g]), m = c(m, i, k, l, a[f + (g + 6) % 16], 9, j[g + 1]), l = c(l, m, i, k, a[f + (g + 11) % 16], 14, j[g + 2]), k = c(k, l, m, i, a[f + g % 16], 20, j[g + 3])) : 48 > g ? (i = d(i, k, l, m, a[f + (3 * g + 5) % 16], 4, j[g]), m = d(m, i, k, l, a[f + (3 * g + 8) % 16], 11, j[g + 1]), l = d(l, m, i, k, a[f + (3 * g + 11) % 16], 16, j[g + 2]), k = d(k, l, m, i, a[f + (3 * g + 14) % 16], 23, j[g + 3])) : (i = e(i, k, l, m, a[f + 3 * g % 16], 6, j[g]), m = e(m, i, k, l, a[f + (3 * g + 7) % 16], 10, j[g + 1]), l = e(l, m, i, k, a[f + (3 * g + 14) % 16], 15, j[g + 2]), k = e(k, l, m, i, a[f + (3 * g + 5) % 16], 21, j[g + 3]));
            h[0] = h[0] + i | 0, h[1] = h[1] + k | 0, h[2] = h[2] + l | 0, h[3] = h[3] + m | 0
        }, _doFinalize: function () {
            var a = this._data, b = a.words, c = 8 * this._nDataBytes, d = 8 * a.sigBytes;
            for (b[d >>> 5] |= 128 << 24 - d % 32, b[(d + 64 >>> 9 << 4) + 14] = 16711935 & (c << 8 | c >>> 24) | 4278255360 & (c << 24 | c >>> 8), a.sigBytes = 4 * (b.length + 1), this._process(), a = this._hash.words, b = 0; 4 > b; b++)c = a[b], a[b] = 16711935 & (c << 8 | c >>> 24) | 4278255360 & (c << 24 | c >>> 8)
        }
    }), f.MD5 = g._createHelper(i), f.HmacMD5 = g._createHmacHelper(i)
}(Math), function () {
    var a = CryptoJS, b = a.lib, c = b.Base, d = b.WordArray, b = a.algo, e = b.EvpKDF = c.extend({
        cfg: c.extend({
            keySize: 4,
            hasher: b.MD5,
            iterations: 1
        }), init: function (a) {
            this.cfg = this.cfg.extend(a)
        }, compute: function (a, b) {
            for (var c = this.cfg, e = c.hasher.create(), f = d.create(), g = f.words, h = c.keySize, c = c.iterations; g.length < h;) {
                i && e.update(i);
                var i = e.update(a).finalize(b);
                e.reset();
                for (var j = 1; c > j; j++)i = e.finalize(i), e.reset();
                f.concat(i)
            }
            return f.sigBytes = 4 * h, f
        }
    });
    a.EvpKDF = function (a, b, c) {
        return e.create(c).compute(a, b)
    }
}(), CryptoJS.lib.Cipher || function (a) {
    var b = CryptoJS, c = b.lib, d = c.Base, e = c.WordArray, f = c.BufferedBlockAlgorithm, g = b.enc.Base64, h = b.algo.EvpKDF, i = c.Cipher = f.extend({
        cfg: d.extend(),
        createEncryptor: function (a, b) {
            return this.create(this._ENC_XFORM_MODE, a, b)
        },
        createDecryptor: function (a, b) {
            return this.create(this._DEC_XFORM_MODE, a, b)
        },
        init: function (a, b, c) {
            this.cfg = this.cfg.extend(c), this._xformMode = a, this._key = b, this.reset()
        },
        reset: function () {
            f.reset.call(this), this._doReset()
        },
        process: function (a) {
            return this._append(a), this._process()
        },
        finalize: function (a) {
            return a && this._append(a), this._doFinalize()
        },
        keySize: 4,
        ivSize: 4,
        _ENC_XFORM_MODE: 1,
        _DEC_XFORM_MODE: 2,
        _createHelper: function () {
            return function (a) {
                return {
                    encrypt: function (b, c, d) {
                        return ("string" == typeof c ? o : n).encrypt(a, b, c, d)
                    }, decrypt: function (b, c, d) {
                        return ("string" == typeof c ? o : n).decrypt(a, b, c, d)
                    }
                }
            }
        }()
    });
    c.StreamCipher = i.extend({
        _doFinalize: function () {
            return this._process(!0)
        }, blockSize: 1
    });
    var j = b.mode = {}, k = c.BlockCipherMode = d.extend({
        createEncryptor: function (a, b) {
            return this.Encryptor.create(a, b)
        }, createDecryptor: function (a, b) {
            return this.Decryptor.create(a, b)
        }, init: function (a, b) {
            this._cipher = a, this._iv = b
        }
    }), j = j.CBC = function () {
        function b(b, c, d) {
            var e = this._iv;
            e ? this._iv = a : e = this._prevBlock;
            for (var f = 0; d > f; f++)b[c + f] ^= e[f]
        }

        var c = k.extend();
        return c.Encryptor = c.extend({
            processBlock: function (a, c) {
                var d = this._cipher, e = d.blockSize;
                b.call(this, a, c, e), d.encryptBlock(a, c), this._prevBlock = a.slice(c, c + e)
            }
        }), c.Decryptor = c.extend({
            processBlock: function (a, c) {
                var d = this._cipher, e = d.blockSize, f = a.slice(c, c + e);
                d.decryptBlock(a, c), b.call(this, a, c, e), this._prevBlock = f
            }
        }), c
    }(), l = (b.pad = {}).Pkcs7 = {
        pad: function (a, b) {
            for (var c = 4 * b, c = c - a.sigBytes % c, d = c << 24 | c << 16 | c << 8 | c, f = [], g = 0; c > g; g += 4)f.push(d);
            c = e.create(f, c), a.concat(c)
        }, unpad: function (a) {
            a.sigBytes -= 255 & a.words[a.sigBytes - 1 >>> 2]
        }
    };
    c.BlockCipher = i.extend({
        cfg: i.cfg.extend({mode: j, padding: l}), reset: function () {
            i.reset.call(this);
            var a = this.cfg, b = a.iv, a = a.mode;
            if (this._xformMode == this._ENC_XFORM_MODE)var c = a.createEncryptor; else c = a.createDecryptor, this._minBufferSize = 1;
            this._mode = c.call(a, this, b && b.words)
        }, _doProcessBlock: function (a, b) {
            this._mode.processBlock(a, b)
        }, _doFinalize: function () {
            var a = this.cfg.padding;
            if (this._xformMode == this._ENC_XFORM_MODE) {
                a.pad(this._data, this.blockSize);
                var b = this._process(!0)
            } else b = this._process(!0), a.unpad(b);
            return b
        }, blockSize: 4
    });
    var m = c.CipherParams = d.extend({
        init: function (a) {
            this.mixIn(a)
        }, toString: function (a) {
            return (a || this.formatter).stringify(this)
        }
    }), j = (b.format = {}).OpenSSL = {
        stringify: function (a) {
            var b = a.ciphertext, a = a.salt, b = (a ? e.create([1398893684, 1701076831]).concat(a).concat(b) : b).toString(g);
            return b = b.replace(/(.{64})/g, "$1\n")
        }, parse: function (a) {
            var a = g.parse(a), b = a.words;
            if (1398893684 == b[0] && 1701076831 == b[1]) {
                var c = e.create(b.slice(2, 4));
                b.splice(0, 4), a.sigBytes -= 16
            }
            return m.create({ciphertext: a, salt: c})
        }
    }, n = c.SerializableCipher = d.extend({
        cfg: d.extend({format: j}), encrypt: function (a, b, c, d) {
            var d = this.cfg.extend(d), e = a.createEncryptor(c, d), b = e.finalize(b), e = e.cfg;
            return m.create({
                ciphertext: b,
                key: c,
                iv: e.iv,
                algorithm: a,
                mode: e.mode,
                padding: e.padding,
                blockSize: a.blockSize,
                formatter: d.format
            })
        }, decrypt: function (a, b, c, d) {
            return d = this.cfg.extend(d), b = this._parse(b, d.format), a.createDecryptor(c, d).finalize(b.ciphertext)
        }, _parse: function (a, b) {
            return "string" == typeof a ? b.parse(a) : a
        }
    }), b = (b.kdf = {}).OpenSSL = {
        compute: function (a, b, c, d) {
            return d || (d = e.random(8)), a = h.create({keySize: b + c}).compute(a, d), c = e.create(a.words.slice(b), 4 * c), a.sigBytes = 4 * b, m.create({
                key: a,
                iv: c,
                salt: d
            })
        }
    }, o = c.PasswordBasedCipher = n.extend({
        cfg: n.cfg.extend({kdf: b}), encrypt: function (a, b, c, d) {
            return d = this.cfg.extend(d), c = d.kdf.compute(c, a.keySize, a.ivSize), d.iv = c.iv, a = n.encrypt.call(this, a, b, c.key, d), a.mixIn(c), a
        }, decrypt: function (a, b, c, d) {
            return d = this.cfg.extend(d), b = this._parse(b, d.format), c = d.kdf.compute(c, a.keySize, a.ivSize, b.salt), d.iv = c.iv, n.decrypt.call(this, a, b, c.key, d)
        }
    })
}(), function () {
    var a = CryptoJS, b = a.lib.BlockCipher, c = a.algo, d = [], e = [], f = [], g = [], h = [], i = [], j = [], k = [], l = [], m = [];
    !function () {
        for (var a = [], b = 0; 256 > b; b++)a[b] = 128 > b ? b << 1 : b << 1 ^ 283;
        for (var c = 0, n = 0, b = 0; 256 > b; b++) {
            var o = n ^ n << 1 ^ n << 2 ^ n << 3 ^ n << 4, o = o >>> 8 ^ 255 & o ^ 99;
            d[c] = o, e[o] = c;
            var p = a[c], q = a[p], r = a[q], s = 257 * a[o] ^ 16843008 * o;
            f[c] = s << 24 | s >>> 8, g[c] = s << 16 | s >>> 16, h[c] = s << 8 | s >>> 24, i[c] = s, s = 16843009 * r ^ 65537 * q ^ 257 * p ^ 16843008 * c, j[o] = s << 24 | s >>> 8, k[o] = s << 16 | s >>> 16, l[o] = s << 8 | s >>> 24, m[o] = s, c ? (c = p ^ a[a[a[r ^ p]]], n ^= a[a[n]]) : c = n = 1
        }
    }();
    var n = [0, 1, 2, 4, 8, 16, 32, 64, 128, 27, 54], c = c.AES = b.extend({
        _doReset: function () {
            for (var a = this._key, b = a.words, c = a.sigBytes / 4, a = 4 * ((this._nRounds = c + 6) + 1), e = this._keySchedule = [], f = 0; a > f; f++)if (c > f)e[f] = b[f]; else {
                var g = e[f - 1];
                f % c ? c > 6 && 4 == f % c && (g = d[g >>> 24] << 24 | d[g >>> 16 & 255] << 16 | d[g >>> 8 & 255] << 8 | d[255 & g]) : (g = g << 8 | g >>> 24, g = d[g >>> 24] << 24 | d[g >>> 16 & 255] << 16 | d[g >>> 8 & 255] << 8 | d[255 & g], g ^= n[f / c | 0] << 24), e[f] = e[f - c] ^ g
            }
            for (b = this._invKeySchedule = [], c = 0; a > c; c++)f = a - c, g = c % 4 ? e[f] : e[f - 4], b[c] = 4 > c || 4 >= f ? g : j[d[g >>> 24]] ^ k[d[g >>> 16 & 255]] ^ l[d[g >>> 8 & 255]] ^ m[d[255 & g]]
        }, encryptBlock: function (a, b) {
            this._doCryptBlock(a, b, this._keySchedule, f, g, h, i, d)
        }, decryptBlock: function (a, b) {
            var c = a[b + 1];
            a[b + 1] = a[b + 3], a[b + 3] = c, this._doCryptBlock(a, b, this._invKeySchedule, j, k, l, m, e), c = a[b + 1], a[b + 1] = a[b + 3], a[b + 3] = c
        }, _doCryptBlock: function (a, b, c, d, e, f, g, h) {
            for (var i = this._nRounds, j = a[b] ^ c[0], k = a[b + 1] ^ c[1], l = a[b + 2] ^ c[2], m = a[b + 3] ^ c[3], n = 4, o = 1; i > o; o++)var p = d[j >>> 24] ^ e[k >>> 16 & 255] ^ f[l >>> 8 & 255] ^ g[255 & m] ^ c[n++], q = d[k >>> 24] ^ e[l >>> 16 & 255] ^ f[m >>> 8 & 255] ^ g[255 & j] ^ c[n++], r = d[l >>> 24] ^ e[m >>> 16 & 255] ^ f[j >>> 8 & 255] ^ g[255 & k] ^ c[n++], m = d[m >>> 24] ^ e[j >>> 16 & 255] ^ f[k >>> 8 & 255] ^ g[255 & l] ^ c[n++], j = p, k = q, l = r;
            p = (h[j >>> 24] << 24 | h[k >>> 16 & 255] << 16 | h[l >>> 8 & 255] << 8 | h[255 & m]) ^ c[n++], q = (h[k >>> 24] << 24 | h[l >>> 16 & 255] << 16 | h[m >>> 8 & 255] << 8 | h[255 & j]) ^ c[n++], r = (h[l >>> 24] << 24 | h[m >>> 16 & 255] << 16 | h[j >>> 8 & 255] << 8 | h[255 & k]) ^ c[n++], m = (h[m >>> 24] << 24 | h[j >>> 16 & 255] << 16 | h[k >>> 8 & 255] << 8 | h[255 & l]) ^ c[n++], a[b] = p, a[b + 1] = q, a[b + 2] = r, a[b + 3] = m
        }, keySize: 8
    });
    a.AES = b._createHelper(c)
}();
var CryptoJS = CryptoJS || function (a, b) {
        var c = {}, d = c.lib = {}, e = d.Base = function () {
            function a() {
            }

            return {
                extend: function (b) {
                    a.prototype = this;
                    var c = new a;
                    return b && c.mixIn(b), c.$super = this, c
                }, create: function () {
                    var a = this.extend();
                    return a.init.apply(a, arguments), a
                }, init: function () {
                }, mixIn: function (a) {
                    for (var b in a)a.hasOwnProperty(b) && (this[b] = a[b]);
                    a.hasOwnProperty("toString") && (this.toString = a.toString)
                }, clone: function () {
                    return this.$super.extend(this)
                }
            }
        }(), f = d.WordArray = e.extend({
            init: function (a, c) {
                a = this.words = a || [], this.sigBytes = c != b ? c : 4 * a.length
            }, toString: function (a) {
                return (a || h).stringify(this)
            }, concat: function (a) {
                var b = this.words, c = a.words, d = this.sigBytes, a = a.sigBytes;
                if (this.clamp(), d % 4)for (var e = 0; a > e; e++)b[d + e >>> 2] |= (c[e >>> 2] >>> 24 - 8 * (e % 4) & 255) << 24 - 8 * ((d + e) % 4); else if (65535 < c.length)for (e = 0; a > e; e += 4)b[d + e >>> 2] = c[e >>> 2]; else b.push.apply(b, c);
                return this.sigBytes += a, this
            }, clamp: function () {
                var b = this.words, c = this.sigBytes;
                b[c >>> 2] &= 4294967295 << 32 - 8 * (c % 4), b.length = a.ceil(c / 4)
            }, clone: function () {
                var a = e.clone.call(this);
                return a.words = this.words.slice(0), a
            }, random: function (b) {
                for (var c = [], d = 0; b > d; d += 4)c.push(4294967296 * a.random() | 0);
                return f.create(c, b)
            }
        }), g = c.enc = {}, h = g.Hex = {
            stringify: function (a) {
                for (var b = a.words, a = a.sigBytes, c = [], d = 0; a > d; d++) {
                    var e = b[d >>> 2] >>> 24 - 8 * (d % 4) & 255;
                    c.push((e >>> 4).toString(16)), c.push((15 & e).toString(16))
                }
                return c.join("")
            }, parse: function (a) {
                for (var b = a.length, c = [], d = 0; b > d; d += 2)c[d >>> 3] |= parseInt(a.substr(d, 2), 16) << 24 - 4 * (d % 8);
                return f.create(c, b / 2)
            }
        }, i = g.Latin1 = {
            stringify: function (a) {
                for (var b = a.words, a = a.sigBytes, c = [], d = 0; a > d; d++)c.push(String.fromCharCode(b[d >>> 2] >>> 24 - 8 * (d % 4) & 255));
                return c.join("")
            }, parse: function (a) {
                for (var b = a.length, c = [], d = 0; b > d; d++)c[d >>> 2] |= (255 & a.charCodeAt(d)) << 24 - 8 * (d % 4);
                return f.create(c, b)
            }
        }, j = g.Utf8 = {
            stringify: function (a) {
                try {
                    return decodeURIComponent(escape(i.stringify(a)))
                } catch (b) {
                    throw Error("Malformed UTF-8 data")
                }
            }, parse: function (a) {
                return i.parse(unescape(encodeURIComponent(a)))
            }
        }, k = d.BufferedBlockAlgorithm = e.extend({
            reset: function () {
                this._data = f.create(), this._nDataBytes = 0
            }, _append: function (a) {
                "string" == typeof a && (a = j.parse(a)), this._data.concat(a), this._nDataBytes += a.sigBytes
            }, _process: function (b) {
                var c = this._data, d = c.words, e = c.sigBytes, g = this.blockSize, h = e / (4 * g), h = b ? a.ceil(h) : a.max((0 | h) - this._minBufferSize, 0), b = h * g, e = a.min(4 * b, e);
                if (b) {
                    for (var i = 0; b > i; i += g)this._doProcessBlock(d, i);
                    i = d.splice(0, b), c.sigBytes -= e
                }
                return f.create(i, e)
            }, clone: function () {
                var a = e.clone.call(this);
                return a._data = this._data.clone(), a
            }, _minBufferSize: 0
        });
        d.Hasher = k.extend({
            init: function () {
                this.reset()
            }, reset: function () {
                k.reset.call(this), this._doReset()
            }, update: function (a) {
                return this._append(a), this._process(), this
            }, finalize: function (a) {
                return a && this._append(a), this._doFinalize(), this._hash
            }, clone: function () {
                var a = k.clone.call(this);
                return a._hash = this._hash.clone(), a
            }, blockSize: 16, _createHelper: function (a) {
                return function (b, c) {
                    return a.create(c).finalize(b)
                }
            }, _createHmacHelper: function (a) {
                return function (b, c) {
                    return l.HMAC.create(a, c).finalize(b)
                }
            }
        });
        var l = c.algo = {};
        return c
    }(Math);
!function () {
    var a = CryptoJS, b = a.lib, c = b.WordArray, b = b.Hasher, d = [], e = a.algo.SHA1 = b.extend({
        _doReset: function () {
            this._hash = c.create([1732584193, 4023233417, 2562383102, 271733878, 3285377520])
        }, _doProcessBlock: function (a, b) {
            for (var c = this._hash.words, e = c[0], f = c[1], g = c[2], h = c[3], i = c[4], j = 0; 80 > j; j++) {
                if (16 > j)d[j] = 0 | a[b + j]; else {
                    var k = d[j - 3] ^ d[j - 8] ^ d[j - 14] ^ d[j - 16];
                    d[j] = k << 1 | k >>> 31
                }
                k = (e << 5 | e >>> 27) + i + d[j], k = 20 > j ? k + ((f & g | ~f & h) + 1518500249) : 40 > j ? k + ((f ^ g ^ h) + 1859775393) : 60 > j ? k + ((f & g | f & h | g & h) - 1894007588) : k + ((f ^ g ^ h) - 899497514), i = h, h = g, g = f << 30 | f >>> 2, f = e, e = k
            }
            c[0] = c[0] + e | 0, c[1] = c[1] + f | 0, c[2] = c[2] + g | 0, c[3] = c[3] + h | 0, c[4] = c[4] + i | 0
        }, _doFinalize: function () {
            var a = this._data, b = a.words, c = 8 * this._nDataBytes, d = 8 * a.sigBytes;
            b[d >>> 5] |= 128 << 24 - d % 32, b[(d + 64 >>> 9 << 4) + 15] = c, a.sigBytes = 4 * b.length, this._process()
        }
    });
    a.SHA1 = b._createHelper(e), a.HmacSHA1 = b._createHmacHelper(e)
}(), function () {
    var a = CryptoJS, b = a.enc.Utf8;
    a.algo.HMAC = a.lib.Base.extend({
        init: function (a, c) {
            a = this._hasher = a.create(), "string" == typeof c && (c = b.parse(c));
            var d = a.blockSize, e = 4 * d;
            c.sigBytes > e && (c = a.finalize(c));
            for (var f = this._oKey = c.clone(), g = this._iKey = c.clone(), h = f.words, i = g.words, j = 0; d > j; j++)h[j] ^= 1549556828, i[j] ^= 909522486;
            f.sigBytes = g.sigBytes = e, this.reset()
        }, reset: function () {
            var a = this._hasher;
            a.reset(), a.update(this._iKey)
        }, update: function (a) {
            return this._hasher.update(a), this
        }, finalize: function (a) {
            var b = this._hasher, a = b.finalize(a);
            return b.reset(), b.finalize(this._oKey.clone().concat(a))
        }
    })
}(), function () {
    var a = CryptoJS, b = a.lib, c = b.Base, d = b.WordArray, b = a.algo, e = b.HMAC, f = b.PBKDF2 = c.extend({
        cfg: c.extend({
            keySize: 4,
            hasher: b.SHA1,
            iterations: 1
        }), init: function (a) {
            this.cfg = this.cfg.extend(a)
        }, compute: function (a, b) {
            for (var c = this.cfg, f = e.create(c.hasher, a), g = d.create(), h = d.create([1]), i = g.words, j = h.words, k = c.keySize, c = c.iterations; i.length < k;) {
                var l = f.update(b).finalize(h);
                f.reset();
                for (var m = l.words, n = m.length, o = l, p = 1; c > p; p++) {
                    o = f.finalize(o), f.reset();
                    for (var q = o.words, r = 0; n > r; r++)m[r] ^= q[r]
                }
                g.concat(l), j[0]++
            }
            return g.sigBytes = 4 * k, g
        }
    });
    a.PBKDF2 = function (a, b, c) {
        return f.create(c).compute(a, b)
    }
}();
var wd = wd || {};
wd.login = wd.login || {};
var warnings = $(".warning");
wd.login.init = function () {
    $(".submit-form").submit(function () {
        return !1
    }), document.onkeydown = function (a) {
        var b = window.event || a, c = b.keyCode || b.which;
        13 == c && $("#login-btn").click()
    }, $("#login-btn").click(function (a) {
        return $(this).attr("disabled") ? !1 : void wd.login.doLogin()
    }), $(".login-github").click(function (a) {
        wd.login.doGithub()
    }), $(".login-coding").click(function (a) {
        wd.login.doCoding()
    }), $(".login-weibo").click(function (a) {
        wd.login.doWeibo()
    }), $(".email-input").focus(function () {
        warnings.eq(0).removeClass("warning-show"), $(this).removeClass("warning-border").addClass("input-focus")
    }).blur(function () {
        return $(this).removeClass("input-focus").removeClass("warning-border"), wd.util.validateEmail($(this).val()) || "" === $(this).val() ? void 0 : ($(".warning").eq(0).text("请输入有效的 Email 地址").addClass("warning-show"), void $(".email-input").addClass("warning-border"))
    }), $(".password").focus(function () {
        warnings.eq(1).removeClass("warning-password-show"), $(this).removeClass("warning-border"), $(this).removeClass("warning-border")
    }).blur(function () {
        $(this).removeClass("warning-border")
    })
}, wd.login.doLogin = function () {
    var a = $("input[name=email]").val(), b = $("input[name=password]").val(), c = $("#auto-login").prop("checked");
    if (!wd.util.validateEmail(a))return warnings.eq(0).text("请输入有效的Email地址").addClass("warning-show"), void $(".email-input").addClass("warning-border");
    if (!wd.util.validatePassword(b))return warnings.eq(1).text("请输入至少8位的字符作为密码").addClass("warning-password-show"), void $(".password").addClass("warning-border").show();
    var d = $("#next").val();
    (null == d || "" == d) && (d = "/dashboard"), $("#login-btn").attr("disabled", "true");
    var e = 1e3, f = 128, g = $("#sk").val(), h = $("#ck").val(), i = CryptoJS.lib.WordArray.random(16).toString(CryptoJS.enc.Hex), j = CryptoJS.lib.WordArray.random(16).toString(CryptoJS.enc.Hex), k = new AesUtil(f, e), l = k.encrypt(j, i, g, b), m = {
        email: a,
        password: [h, j, i, l].join("."),
        autologin: c
    };
    $.ajax({
        url: "/admin/login",
        type: "POST",
        data: {
            email: a,
            password: b
        },
        cache: !1,
        timeout: 3e4,
        dataType: "json",
        success: function (data) {
            if (data.resultInfo == "invalid password") {
                $("#login-btn").removeAttr("disabled"), warnings.eq(1).text("密码错误").addClass("warning-password-show")
            } else
            // ，登录验证通过，自动跳转到主页面
                window.location.href = "/admin/dashboard";
            // window.location.href("/student/dashboard");

            var f = b.code;
            0 == f ? ("undefined" != typeof mixpanel && (mixpanel.identify(a), mixpanel.people.set({$email: a})), setTimeout(function () {
                window.location.href = d
            }, 500)) : 1 == f && ($("#login-btn").removeAttr("disabled"), warnings.eq(1).text("学号或Email或密码错误").addClass("warning-password-show"))
        },
        error: function () {
            alert("fail\n");
            $("#login-btn").removeAttr("disabled"), warnings.eq(1).text("学号或密码错误").addClass("warning-password-show")
        }
    })
}, wd.util = wd.util || {}, wd.util.validateEmail = function (a) {
    return /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/.test(a)
}, wd.util.validatePassword = function (a) {
    return a.length >= 8
};
var AesUtil = function (a, b) {
    this.keySize = a / 32, this.iterationCount = b
};
AesUtil.prototype.generateKey = function (a, b) {
    return CryptoJS.PBKDF2(b, CryptoJS.enc.Hex.parse(a), {keySize: this.keySize, iterations: this.iterationCount})
}, AesUtil.prototype.encrypt = function (a, b, c, d) {
    var e = this.generateKey(a, c), f = CryptoJS.AES.encrypt(d, e, {iv: CryptoJS.enc.Hex.parse(b)});
    return f.ciphertext.toString(CryptoJS.enc.Base64)
}, AesUtil.prototype.decrypt = function (a, b, c, d) {
    var e = this.generateKey(a, c), f = CryptoJS.lib.CipherParams.create({ciphertext: CryptoJS.enc.Base64.parse(d)}), g = CryptoJS.AES.decrypt(f, e, {iv: CryptoJS.enc.Hex.parse(b)});
    return g.toString(CryptoJS.enc.Utf8)
};